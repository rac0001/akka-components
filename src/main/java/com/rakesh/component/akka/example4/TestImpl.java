package com.rakesh.component.akka.example4;

import akka.actor.*;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by mac on 3/19/17.
 */
public class TestImpl {

    public static void main(String args[]){

        ActorSystem fileSystem = ActorSystem.create("basics");

        ActorRef askActor = fileSystem.actorOf(Props.create(AskActor.class),"ask");
        ActorRef selActor = fileSystem.actorOf(Props.create(SelectionActor.class),"sel");


/*        final Inbox inbox = Inbox.create(fileSystem);
        inbox.watch(askActor);
        inbox.send(askActor,"Hello");

        try {
            inbox.receive(Duration.create(1, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            System.out.println("actorRef:"+inbox.getRef()+","+e);
        }
        */
    }


}
