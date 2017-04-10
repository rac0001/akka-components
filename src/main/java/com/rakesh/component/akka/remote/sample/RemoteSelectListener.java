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
public class RemoteSelectListener {

    public static void main(String args[]){

        Config config= ConfigFactory.load().getConfig("RemoteLookupListener");

        ActorSystem actorSystem = ActorSystem.create("remote-system-1",config);

//        ActorSelection actorSelection = actorSystem.actorSelection("akka.tcp://remote-system@127.0.0.1:1550/user/remote-worker");

        ActorSelection actorSelection = actorSystem.actorSelection("akka.tcp://ClusterSystem@127.0.0.1:2557/user/clusterListener");

        actorSelection.tell("Hey remote guy, from selector" , ActorRef.noSender());

    }

}
