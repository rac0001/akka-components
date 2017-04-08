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

    private Cluster cluster;

    @Override
    public void preStart(){
        cluster = Cluster.get(getContext().system());
    }

    @Override
    public void postStop(){
        cluster.unsubscribe(self());
    }

    public Backend() {
        receive(ReceiveBuilder
                .match(String.class , msg -> {
                    sender().tell( new ResultMessage(msg.toUpperCase()),self());})
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
