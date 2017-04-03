package com.rakesh.component.akka.workerdialin;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by mac on 3/26/17.
 */
public class ExampleRouterActor extends AbstractActor {


    public ExampleRouterActor(){

        receive(ReceiveBuilder
                .match(String.class,line -> System.out.println(line))
                .matchAny(this::unhandled)
                .build()
        );

    }
}
