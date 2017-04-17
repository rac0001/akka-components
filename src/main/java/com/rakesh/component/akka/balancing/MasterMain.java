package com.rakesh.component.akka.balancing;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ranantoju on 4/15/2017.
 */
public class MasterMain {

    public static void main(String args[]){

        Config config= ConfigFactory.load().getConfig("FrontEndCluster");

        ActorSystem actorSystem = ActorSystem.create("load-balancing-system",config);

        final ActorRef master = actorSystem.actorOf(Props.create(Master.class),"master");

//        ActorRef workResolver = actorSystem.actorOf(Props.create(WorkProcessor.class),"work-processor");

        AtomicInteger i = new AtomicInteger(0);
        actorSystem.scheduler().schedule(Duration.create(25, TimeUnit.SECONDS),Duration.create(2, TimeUnit.SECONDS),
                () -> {
                    Work work = new Work("hello, this is work-"+i.incrementAndGet());
                    master.tell(work, ActorRef.noSender());
                }, actorSystem.dispatcher());

    }

}
