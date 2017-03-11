package com.rakesh.component.akka.example2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by ranantoju on 3/11/2017.
 */
public class RecorderImpl {

    public static void main(String args[]){

        final ActorSystem actorSystem = ActorSystem.create("recording-system");

        final ActorRef storage = actorSystem.actorOf(Props.create(Storage.class),"storage");
        final ActorRef checker = actorSystem.actorOf(Props.create(Checker.class));

        final ActorRef recorder = actorSystem.actorOf(Props.create(Recorder.class,checker,storage));
        User user = new User("Rakesh","rakesh@gmail.com");
        recorder.tell(user,ActorRef.noSender());

//        User user1 = new User("Adam","adam@gmail.com");
//        recorder.tell(user1,recorder);


    }


}
