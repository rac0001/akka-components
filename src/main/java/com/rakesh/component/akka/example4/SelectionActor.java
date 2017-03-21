package com.rakesh.component.akka.example4;

import akka.actor.*;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by mac on 3/19/17.
 */
public class SelectionActor extends AbstractActor {

    public SelectionActor(){

        System.out.println("--from sel--");
        ActorSelection sel = getContext().actorSelection("/user/ask");

        sel.tell(new Identify(""),self());

        receive(ReceiveBuilder.match(
                ActorIdentity.class,msg -> {
                    ActorRef ref = msg.getRef();
                    ref.tell("hello from sel",self());
                }).build()
        );

    }
}
