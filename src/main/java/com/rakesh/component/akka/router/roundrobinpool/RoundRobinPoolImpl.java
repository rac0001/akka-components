package com.rakesh.component.akka.router.roundrobinpool;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by ranantoju on 4/1/2017.
 */
public class RoundRobinPoolImpl {


    public static void main(String args[]){

        Config config= ConfigFactory.load().getConfig("RouterPool");

        ActorSystem fileSystem = ActorSystem.create("router-example",config);

        final ActorRef fileActor = fileSystem.actorOf(Props.create(DispatchMessageActor.class),"round-robin-dispatch");

        fileActor.tell("hellllo", ActorRef.noSender());
//        fileActor.tell("bellllo", ActorRef.noSender());


    }

}
