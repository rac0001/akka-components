package com.rakesh.component.akka.router.roundrobinpool;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ranantoju on 4/1/2017.
 */
public class RoundRobinPoolImpl {


    public static void main(String args[]){

        Config config= ConfigFactory.load().getConfig("RouterPool");

        ActorSystem actorSystem = ActorSystem.create("router-example",config);

        final ActorRef fileActor = actorSystem.actorOf(Props.create(DispatchMessageActor.class),"round-robin-dispatch");

        fileActor.tell("hellllo", ActorRef.noSender());
//        fileActor.tell("bellllo", ActorRef.noSender());
        AtomicInteger i = new AtomicInteger(0);
        actorSystem.scheduler().schedule(Duration.create(5, TimeUnit.SECONDS),Duration.create(1, TimeUnit.SECONDS),
               () -> fileActor.tell("hellllo-"+i.incrementAndGet(), ActorRef.noSender())
                , actorSystem.dispatcher());


    }

}
