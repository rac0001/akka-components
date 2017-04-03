package com.rakesh.component.akka.remote.sample;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by mac on 4/2/17.
 */
public class Worker extends AbstractActor {

    Worker(){

        receive(ReceiveBuilder
                .match(String.class, msg -> {
                    System.out.println("path is:"+self().path());
                    System.out.println("self is:"+self());
                    System.out.println("recieved message:"+msg);
//                    getContext().system().terminate();
                })
                .build()

        );


    }



}
