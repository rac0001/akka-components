package com.rakesh.component.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActor;

/**
 * Created by mac on 3/11/17.
 */
public class AkkaImpl {

    public static void main(String args[]){

        final ActorSystem actorSystem = ActorSystem.create("Greeting-system");

        final ActorRef myActor = actorSystem.actorOf(Props.create(Greeter.class), "greeter");

        myActor.tell("Hello",myActor);

        actorSystem.shutdown();

    }

}
