package com.rakesh.component.akka.remote.sample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by mac on 4/2/17.
 */
public class RemoteListener1 {

    public static void main(String args[]){

        Config config= ConfigFactory.load().getConfig("RemoteListener1");

        ActorSystem actorSystem = ActorSystem.create("remote-system-1",config);

        ActorRef workerRef = actorSystem.actorOf(Props.create(Worker.class),"remote-worker-1");

        workerRef.tell("Hey remote guy 1" , ActorRef.noSender());

    }
}
