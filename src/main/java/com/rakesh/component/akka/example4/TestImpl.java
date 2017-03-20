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

        Props askProp = Props.create(AskActor.class);
        ActorRef askActor = fileSystem.actorOf(askProp,"ask");

        askActor.tell("Hello",ActorRef.noSender());

//        ActorSelection act= fileSystem.actorSelection("/basics/user/ask");
//        act.tell(new Identify("hello from selection"),askActor);


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
