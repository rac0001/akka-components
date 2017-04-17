package com.rakesh.component.akka.state;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import com.rakesh.component.akka.example4.AskActor;
import scala.concurrent.Future;

/**
 * Created by mac on 4/14/17.
 */
public class StateImpl {


    public static void main(String args[]){

        ActorSystem fileSystem = ActorSystem.create("state-system");

        ActorRef stateRef = fileSystem.actorOf(Props.create(ActorState.class),"state");

        stateRef.tell("foo",ActorRef.noSender());

        stateRef.tell("bar",ActorRef.noSender());

        stateRef.tell("bar",ActorRef.noSender());

        stateRef.tell("foo",ActorRef.noSender());

//        stateRef.tell("foo",ActorRef.noSender());
//        Future<Terminated> ter = fileSystem.whenTerminated();

    }

}
