package com.rakesh.component.akka.router.custom;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by ranantoju on 4/1/2017.
 */
public class WorkerActor extends AbstractActor {

    WorkerActor(){
        receive(ReceiveBuilder.match(
                String.class,msg -> {
                    System.out.println("received message-->"+msg+"::::"+self().path().toString());
                    sender().tell("over",self());
                }
                ).build()
        );
    }

}
