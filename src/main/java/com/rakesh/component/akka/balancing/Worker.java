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

/**
 * Created by ranantoju on 4/8/2017.
 */
public class Worker extends AbstractActor {

//    private Cluster cluster = Cluster.get(getContext().system());
    public ActorRef master;

/*
    @Override
    public void preStart(){
        cluster.subscribe(self(), ClusterEvent.initialStateAsEvents(),
                ClusterEvent.MemberEvent.class, ClusterEvent.UnreachableMember.class);

    }
*/

    public void doWork(Work work,ActorRef sender){

        context().system().scheduler().scheduleOnce(Duration.create(1, TimeUnit.SECONDS),
                () -> {
                    getContext().actorSelection("/user/worker-resolver").tell(work,self());
                },context().system().dispatcher());
//        System.out.println("from remote worker:"+sender.path()+" and work is:"+work.getWorkRequestParam());
    };

/*
    @Override
    public void postStop(){
        cluster.unsubscribe(self());
    }
*/

    public Worker(ActorRef master) {
        this.master = master;
        receive(idle());
    }

    public void register(Member member){
        if(member.hasRole("master")){
            getContext().actorSelection(member.address()+"/user/master")
                    .tell(new MessageHandler.InitializeWorkers(self()),self());
        }
    }


    final PartialFunction<Object,BoxedUnit> idle(){
        return ReceiveBuilder
                .match(MessageHandler.WorkReady.class, msg -> {
//                    master = sender();
                    master.tell(new MessageHandler.RequestWork(self()),self());
                })
                .match(MessageHandler.NoWork.class , msg -> {})
                .match(MessageHandler.DoWork.class, msg -> {

                    doWork(msg.getWork(),self());

                    context().become(working());
                })
                /*.match(ClusterEvent.CurrentClusterState.class, state -> {
                    for(Member member : state.getMembers()){
                        if (member.status().equals(MemberStatus.up())) {
                            register(member);
                        }
                    }
                })
                .match(ClusterEvent.MemberUp.class, mUp -> {
                    register(mUp.member());
                })*/
                .build();
    }


    final PartialFunction<Object,BoxedUnit> working(){
        return ReceiveBuilder
                .match(MessageHandler.DoWork.class, msg -> {})
                .match(MessageHandler.WorkReady.class, msg -> {
//                    master = sender();
                })
                .match(MessageHandler.NoWork.class , msg -> {})
                .match(MessageHandler.WorkCompleted.class, completed ->{
                    master.tell(new MessageHandler.WorkIsDone(self()),self());
                    master.tell(new MessageHandler.RequestWork(self()),self());
                    context().become(idle());
                })
                /*.match(ClusterEvent.CurrentClusterState.class, state -> {
                    for(Member member : state.getMembers()){
                        if (member.status().equals(MemberStatus.up())) {
                            register(member);
                        }
                    }
                })
                .match(ClusterEvent.MemberUp.class, mUp -> {
                    register(mUp.member());
                })*/
                .build();
    }
}
