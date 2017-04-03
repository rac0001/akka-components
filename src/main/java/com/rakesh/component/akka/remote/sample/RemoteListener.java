package com.rakesh.component.akka.remote.sample;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by mac on 4/2/17.
 */
public class RemoteListener {

    public static void main(String args[]){

        Config config= ConfigFactory.load().getConfig("RemoteListener");

        ActorSystem actorSystem = ActorSystem.create("remote-system",config);

        ActorRef workerRef = actorSystem.actorOf(Props.create(Worker.class),"remote-worker");

        workerRef.tell("Hey remote guy" , ActorRef.noSender());

    }
}
