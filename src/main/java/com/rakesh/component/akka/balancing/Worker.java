package com.rakesh.component.akka.balancing;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;
import akka.cluster.MemberStatus;
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
    public ActorRef master ;
    public ActorRef workResolver;

    public AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void preStart(){
        cluster.subscribe(self(), ClusterEvent.initialStateAsEvents(),
                ClusterEvent.MemberEvent.class, ClusterEvent.UnreachableMember.class);
    }

    public void doWork(Work work){

        context().system().scheduler().scheduleOnce(Duration.create(20, TimeUnit.SECONDS),
                () -> getContext().actorSelection("/user/work-processor").tell(work,self())
                ,context().system().dispatcher());
    };

    @Override
    public void postStop(){
        cluster.unsubscribe(self());
    }

    public Worker(ActorRef workResolver) {
        this.workResolver = workResolver;
        receive(idle());
    }

    public void register(Member member){
        System.out.println("----inside register----");
        if(member.hasRole("master")){
            System.out.println("----master register----");
            getContext().actorSelection(member.address()+"/user/master/singleton")
                    .tell(new MessageHandler.InitializeWorkers(self()),self());
        }
    }

    final PartialFunction<Object,BoxedUnit> idle(){
        return ReceiveBuilder
                .match(MessageHandler.WorkReady.class, msg -> {
                    master = sender();
                    master.tell(new MessageHandler.RequestWork(self()),self());
                })
                .match(MessageHandler.NoWork.class , msg -> {})
                .match(MessageHandler.DoWork.class, msg -> {
//                    counter.incrementAndGet();
//                    System.out.println("counter--"+counter.get());
                    doWork(msg.getWork());
//                    if(counter.get() == 2) {
                        System.out.println("--now become working");
                        context().become(working());
//                    }
                })/*.match(MessageHandler.WorkCompleted.class, completed ->{
//                    counter.decrementAndGet();
//                    System.out.println("counter--"+counter.get());
                    master.tell(new MessageHandler.WorkIsDone(self()),self());
                    master.tell(new MessageHandler.RequestWork(self()),self());
                })*/
                .match(ClusterEvent.CurrentClusterState.class, state -> {
                    for(Member member : state.getMembers()){
                        if (member.status().equals(MemberStatus.up())) {
                            register(member);
                        }
                    }
                })
                .match(ClusterEvent.MemberUp.class, mUp -> {
                    register(mUp.member());
                })
                .build();
    }

    final PartialFunction<Object,BoxedUnit> working(){
        return ReceiveBuilder
                .match(MessageHandler.DoWork.class, msg -> {})
                .match(MessageHandler.WorkReady.class, msg -> {
                    master = sender();
                })
                .match(MessageHandler.NoWork.class , msg -> {})
                .match(MessageHandler.WorkCompleted.class, completed ->{
//                    counter.decrementAndGet();
//                    System.out.println("counter--"+counter.get());
                    master.tell(new MessageHandler.WorkIsDone(self()),self());
                    master.tell(new MessageHandler.RequestWork(self()),self());
//                    if(counter.get() == 0) {
                        System.out.println("--now become idle");
                        context().become(idle());
//                    }
                })
                .match(ClusterEvent.CurrentClusterState.class, state -> {
                    for(Member member : state.getMembers()){
                        if (member.status().equals(MemberStatus.up())) {
                            register(member);
                        }
                    }
                })
                .match(ClusterEvent.MemberUp.class, mUp -> {
                    register(mUp.member());
                })
                .build();
    }
}
