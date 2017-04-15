package com.rakesh.component.akka.router.customrouter;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

import java.io.IOException;

/**
 * Created by mac on 4/2/17.
 */
public class Worker extends AbstractActor {

    Worker() {

        receive(ReceiveBuilder
                        .match(String.class, msg -> {
                            System.out.println(self().path().toString()+"-received message->" + msg);
//                            System.out.println(self().path().toString());
//                    sender().tell("send back to master",self());
                        })
                        .build()

        );


    }


}
