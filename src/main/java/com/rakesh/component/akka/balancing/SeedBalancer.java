package com.rakesh.component.akka.balancing;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by ranantoju on 4/8/2017.
 */
public class SeedBalancer extends AbstractActor {

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


    public SeedBalancer(){

        receive(ReceiveBuilder
                .match(String.class , msg -> {
                    System.out.println("---------->"+self()+"--->"+msg);
                })
                .build()
        );

    }
}
