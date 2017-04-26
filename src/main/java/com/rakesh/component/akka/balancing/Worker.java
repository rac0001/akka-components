package com.rakesh.component.akka.balancing;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;
import akka.cluster.MemberStatus;
import akka.cluster.singleton.ClusterSingletonProxy;
import akka.cluster.singleton.ClusterSingletonProxySettings;
import akka.japi.pf.ReceiveBuilder;
import com.rakesh.component.akka.Transformation.ResultMessage;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;
import scala.runtime.BoxedUnit;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ranantoju on 4/8/2017.
 */
public class Worker extends AbstractActor {

    private Cluster cluster = Cluster.get(getContext().system());
    public ActorRef masterProxy;
    public ActorRef workProcessor;

//    public AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void preStart(){
        cluster.subscribe(self(), ClusterEvent.initialStateAsEvents(),
                ClusterEvent.MemberEvent.class, ClusterEvent.UnreachableMember.class);
    }

    public void doWork(Work work){

//        workProcessor.tell(work,self());

        context().system().scheduler().scheduleOnce(Duration.create(15, TimeUnit.SECONDS),
                () -> workProcessor.tell(work,self())
                ,context().system().dispatcher());

    };

    @Override
    public void postStop(){
        cluster.unsubscribe(self());
    }

    public Worker() {

        masterProxy = getContext().actorOf(ClusterSingletonProxy.props("/user/master",
                        ClusterSingletonProxySettings.create(getContext().system()).withRole("master")),
                "masterProxy");

        this.workProcessor = getContext().system().actorOf(Props.create(WorkProcessor.class),"work-processor");

        receive(idle());
    }

        public void register(Member member){
        if(member.hasRole("worker")){
            System.out.println("----worker register----");
            masterProxy.tell(new MessageHandler.InitializeWorkers(self()),self());
/*
            getContext().actorSelection(member.address()+"/user/master/singleton")
                    .tell(new MessageHandler.InitializeWorkers(self()),self());
*/
        }
    }

    final PartialFunction<Object,BoxedUnit> idle(){
        return ReceiveBuilder
                .match(MessageHandler.WorkReady.class, msg -> {
//                    master = sender();
                    masterProxy.tell(new MessageHandler.RequestWork(self()),self());
                })
                .match(MessageHandler.NoWork.class , msg -> {})
                .match(MessageHandler.DoWork.class, msg -> {
//                    counter.incrementAndGet();
//                    System.out.println("counter--"+counter.get());
                    doWork(msg.getWork());
//                    if(counter.get() == 2) {
                        System.out.println("--now become working--");
                        context().become(working());
//                    }
                })/*.match(ClusterEvent.CurrentClusterState.class, state -> {
                    for(Member member : state.getMembers()){
                        if (member.status().equals(MemberStatus.up())) {
                            register(member);
                        }
                    }
                })*/
                .match(MessageHandler.WorkCompleted.class, completed ->{
//                    System.out.println("--now become WorkCompleted from idleee");
                }).match(ClusterEvent.MemberUp.class, mUp -> {
                    register(mUp.member());
                })
                .build();
    }

    final PartialFunction<Object,BoxedUnit> working(){
        return ReceiveBuilder
                .match(MessageHandler.DoWork.class, msg -> {})
                .match(MessageHandler.WorkReady.class, msg -> {})
                .match(MessageHandler.NoWork.class , msg -> {})
                .match(MessageHandler.WorkCompleted.class, completed ->{
//                    System.out.println("--now become WorkCompleted");

//                    counter.decrementAndGet();
//                    System.out.println("counter--"+counter.get());
                    masterProxy.tell(new MessageHandler.WorkIsDone(self()),self());
                    masterProxy.tell(new MessageHandler.RequestWork(self()),self());
//                    if(counter.get() == 0) {
                        System.out.println("--now become free--");
                        context().become(idle());
//                    }
                })
                .build();
    }
}
