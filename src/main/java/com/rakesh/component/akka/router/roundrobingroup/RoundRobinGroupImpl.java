package com.rakesh.component.akka.router.roundrobingroup;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by ranantoju on 4/1/2017.
 */
public class RoundRobinGroupImpl {

    public static void main(String args[]){


        ActorSystem fileSystem = ActorSystem.create("router");
        fileSystem.actorOf(Props.create(Worker.class),"worker-1");
        fileSystem.actorOf(Props.create(Worker.class),"worker-2");
        fileSystem.actorOf(Props.create(Worker.class),"worker-3");

        Props fileProps = Props.create(DispatchMessageActor.class);

        final ActorRef fileActor = fileSystem.actorOf(fileProps,"round-robin-group-dispatch");

        fileActor.tell("hellllo", ActorRef.noSender());
        fileActor.tell("bellllo", ActorRef.noSender());

    }

}
