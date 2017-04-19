package com.rakesh.component.akka.router.cluster;

import akka.actor.AbstractActor;
import akka.actor.Cancellable;
import akka.cluster.Cluster;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by mac on 4/17/17.
 */
public class ClusterWorker extends AbstractActor {


    Cluster cluster = Cluster.get(getContext().system());

    @Override
    public void postStop() {
        cluster.unsubscribe(self());
    }


    ClusterWorker() {

        receive(ReceiveBuilder
                .match(MessageProcess.class, msg -> {
                    System.out.println("---Received message from router----" + msg.getMessage() + "--path---" + self().path());


                    getContext().system().scheduler().scheduleOnce(Duration.create(20, TimeUnit.SECONDS),
                            () -> {
                                System.out.println("---the worker still working on same message----" +
                                    msg.getMessage() + "--path---" + self().path());
//                                sender().tell(new WorkComplete(msg.getMessage()),self());
                            }
                            , getContext().system().dispatcher());

                })
                .matchAny(msg -> {
                    System.out.println("---got *any* from router----" + self().path());
                })
                .build()
        );
    }

}


