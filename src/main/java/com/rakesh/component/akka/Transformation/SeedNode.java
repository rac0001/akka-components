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
public class SeedNode extends AbstractActor {

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


    public SeedNode(){

        receive(ReceiveBuilder
                .match(String.class , msg -> {
                    System.out.println("---------->"+self()+"--->"+msg);
                })
                .build()
        );

    }
}
