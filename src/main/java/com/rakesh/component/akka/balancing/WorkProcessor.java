package com.rakesh.component.akka.balancing;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by mac on 4/15/17.
 */
public class WorkProcessor extends AbstractActor {

    public WorkProcessor() {

        receive(ReceiveBuilder
                        .match(Work.class, work -> {

//                    Process proc = Runtime.getRuntime().exec("java -cp IngestionTrack-1.0-SNAPSHOT.jar Ingestion_Start");

                            System.out.println("from remote worker:"+sender().path()
                                    +" and work is:"+work.getWorkRequestParam());

//                    proc.waitFor();
                            //loop

                            sender().tell(new MessageHandler.WorkCompleted(),self());
                        })
                        .build()
        );
    }

}
