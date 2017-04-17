package com.rakesh.component.akka.router.roundrobingroup;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.rakesh.component.akka.Transformation.AddMessage;
import com.rakesh.component.akka.remote.sample.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ranantoju on 4/1/2017.
 */
public class RoundRobinGroupImpl {

    public static void main(String args[]){

        Config config= ConfigFactory.load().getConfig("RouterPool");

        ActorSystem actorSystem = ActorSystem.create("router",config);

        final ActorRef fileActor = actorSystem.actorOf(Props.create(DispatchMessageActor.class),"router-dispatch");
//        ActorRef workerRef = actorSystem.actorOf(Props.create(DispatchMessageActor.class),"round-robin-group-dispatch");

/*

        ActorSystem fileSystem = ActorSystem.create("router");
//        fileSystem.actorOf(Props.create(Worker.class),"worker-1");
//        fileSystem.actorOf(Props.create(Worker.class),"worker-2");
//        fileSystem.actorOf(Props.create(Worker.class),"worker-3");

        final ActorRef fileActor = fileSystem.actorOf(Props.create(DispatchMessageActor.class),"round-robin-group-dispatch");
*/

//        fileActor.tell("hellllo", ActorRef.noSender());
//        fileActor.tell("xellllo", ActorRef.noSender());
//        fileActor.tell("bellllo", ActorRef.noSender());

        AtomicInteger i = new AtomicInteger(0);
        actorSystem.scheduler().schedule(Duration.create(1, TimeUnit.SECONDS),Duration.create(1, TimeUnit.SECONDS),
                new Runnable() {
                    @Override
                    public void run() {
                        fileActor.tell("hellllo-"+i.incrementAndGet(), ActorRef.noSender());
                    }
                }, actorSystem.dispatcher());

    }

}
