package com.rakesh.component.Worker;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by ranantoju on 4/11/2017.
 */
public class JobExecutor extends AbstractActor {

    public JobExecutor() {
        receive(ReceiveBuilder.match(JobExecutor.class, msg -> {
                    System.out.println(" <<<<<<<<<<<<, I am starting a shell script>>>>>>>>>>>. ");
                }
        ).build());
    }
}
