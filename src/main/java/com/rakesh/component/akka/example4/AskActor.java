package com.rakesh.component.akka.example4;

import akka.actor.*;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by mac on 3/19/17.
 */
public class AskActor extends AbstractActor {

    public AskActor(){

        System.out.println("--from ask--");

        receive(ReceiveBuilder
                .match(String.class,msg -> {
                    System.out.println(" message is : "+msg);
                }).build()
        );

//        getContext().system().shutdown();
    }

}
