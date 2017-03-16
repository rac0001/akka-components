package com.rakesh.component.akka.example0;

import akka.actor.AbstractActor;
import akka.actor.UntypedActor;
import akka.japi.pf.ReceiveBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mac on 3/11/17.
 */
public class Greeter extends AbstractActor {

    public static Logger logger = LoggerFactory.getLogger(Greeter.class);

    public Greeter() {
        receive(ReceiveBuilder
                .match(String.class, s -> {
                    System.out.println("Received String message: "+ s);
                })
                .matchAny(o -> System.out.println("received unknown message")).build()
        );
    }


}
