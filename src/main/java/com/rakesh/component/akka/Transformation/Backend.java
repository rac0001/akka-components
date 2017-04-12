package com.rakesh.component.akka.Transformation;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;
import akka.cluster.MemberStatus;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by ranantoju on 4/8/2017.
 */
public class Backend extends AbstractActor {

    private Cluster cluster = Cluster.get(getContext().system());

    @Override
    public void preStart(){
        cluster.subscribe(self(), ClusterEvent.initialStateAsEvents(),
                ClusterEvent.MemberEvent.class, ClusterEvent.UnreachableMember.class);

    }

    @Override
    public void postStop(){
        cluster.unsubscribe(self());
    }

    public Backend() {
        receive(ReceiveBuilder
                .match(String.class , msg -> {
                    System.out.println("---------->"+self()+"--->"+msg);
                    sender().tell( new ResultMessage(msg.toUpperCase()),self());})
                .match(ClusterEvent.CurrentClusterState.class, state -> {
                    for(Member member : state.getMembers()){
                        System.out.println("########up member"+member.getRoles());

                        if (member.status().equals(MemberStatus.up())) {
                            register(member);
                        }
                    }
                })
                .match(ClusterEvent.MemberUp.class, mUp -> {
                    System.out.println("########up member"+mUp.member().getRoles());
                    register(mUp.member());
                })
                .build()
        );
    }

    public void register(Member member){
        if(member.hasRole("frontend")){
            System.out.println("########registering");
            getContext().actorSelection(member.address()+"/user/frontend").tell("backendWorker",self());
        }
    }
}
