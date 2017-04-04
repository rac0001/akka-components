package com.rakesh.component.akka.router.roundrobingroup;

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
                    System.out.println("received message->"+msg);
                    System.out.println(self().path().toString());
                    getContext().sender().tell(PoisonPill.getInstance(),self());
                }
                ).build()
        );
    }

}
