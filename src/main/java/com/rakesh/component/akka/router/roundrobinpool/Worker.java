package com.rakesh.component.akka.router.roundrobinpool;

import akka.actor.AbstractActor;
import akka.actor.Cancellable;
import akka.actor.PoisonPill;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by ranantoju on 4/1/2017.
 */
public class Worker extends AbstractActor {

    Worker(){
        receive(ReceiveBuilder.match(
                String.class,msg -> {
                    System.out.println("received message-->"+msg+"::::"+self().path());
                    getContext().system().scheduler().scheduleOnce(Duration.create(10, TimeUnit.SECONDS),
                            () -> {
                                System.out.println("---the worker still working on same message----" +
                                        msg + "--path---" + self().path());
//                                sender().tell(new WorkComplete(msg.getMessage()),self());
                            }
                            , getContext().system().dispatcher());

                    sender().tell(new Result("over"),self());
                }
                ).build()
        );
    }

}
