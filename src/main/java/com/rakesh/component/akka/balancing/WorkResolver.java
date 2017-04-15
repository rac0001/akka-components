package com.rakesh.component.akka.balancing;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by mac on 4/15/17.
 */
public class WorkResolver extends AbstractActor {

    public WorkResolver() {

        receive(ReceiveBuilder
                .match(Work.class, work -> {
                    System.out.println("from remote worker:"+sender().path()
                            +" and work is:"+work.getWorkRequestParam());
                })
                .build()
        );
    }

}
