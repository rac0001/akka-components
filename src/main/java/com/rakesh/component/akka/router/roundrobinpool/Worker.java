package com.rakesh.component.akka.router.roundrobinpool;

import akka.actor.AbstractActor;
import akka.actor.PoisonPill;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by ranantoju on 4/1/2017.
 */
public class Worker extends AbstractActor {

    Worker(){
        receive(ReceiveBuilder.match(
                String.class,msg -> {
                    System.out.println("received message-->"+msg+"::::"+self().path().toString());
                    sender().tell("over",self());
                }
                ).build()
        );
    }

}
