package com.rakesh.component.akka.example4;

import akka.actor.AbstractActor;
import akka.actor.ActorIdentity;
import akka.actor.Identify;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by mac on 3/19/17.
 */
public class SelectionActor extends AbstractActor {

    public SelectionActor(){

        System.out.println("--from sel--");

        receive(ReceiveBuilder
                .match(ActorIdentity.class, msg -> {
                    System.out.println("received message is : "+msg);
                }).build()
        );

    }
}
