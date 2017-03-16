package com.rakesh.component.akka.example0;

import akka.actor.*;

/**
 * Created by mac on 3/11/17.
 */
public class AkkaImpl {

    public static void main(String args[]){

        final ActorSystem actorSystem = ActorSystem.create("Greeting-system");

        final ActorRef myActor = actorSystem.actorOf(Props.create(Greeter.class));

        myActor.tell("Hello",ActorRef.noSender());

        myActor.tell(PoisonPill.getInstance(),ActorRef.noSender());
    }

}
