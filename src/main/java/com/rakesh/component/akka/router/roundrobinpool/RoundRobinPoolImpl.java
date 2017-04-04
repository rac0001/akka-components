package com.rakesh.component.akka.router.roundrobinpool;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by ranantoju on 4/1/2017.
 */
public class RoundRobinPoolImpl {


    public static void main(String args[]){


        ActorSystem fileSystem = ActorSystem.create("router-example");

        Props fileProps = Props.create(DispatchMessageActor.class);

        final ActorRef fileActor = fileSystem.actorOf(fileProps,"round-robin-dispatch");

        fileActor.tell("hellllo", ActorRef.noSender());
        fileActor.tell("bellllo", ActorRef.noSender());


    }

}
