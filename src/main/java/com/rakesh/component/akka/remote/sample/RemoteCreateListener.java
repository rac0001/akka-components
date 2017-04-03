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
public class RemoteCreateListener {

    public static void main(String args[]){

        Config config= ConfigFactory.load().getConfig("RemoteCreateListener");

        ActorSystem actorSystem = ActorSystem.create("remote-system-2",config);

        ActorRef workerRef = actorSystem.actorOf(Props.create(Worker.class),"remote-worker-create");

        workerRef.tell("Hey remote create guy" , ActorRef.noSender());


    }
}
