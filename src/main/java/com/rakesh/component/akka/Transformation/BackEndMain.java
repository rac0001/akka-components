package com.rakesh.component.akka.Transformation;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by ranantoju on 4/8/2017.
 */
public class BackEndMain {

    public static void main(String args[]){

        String port = args.length == 0 ? "0" : args[0];
        String host = args.length == 0 ? "10.248.66.129" : args[1];

        Config config= ConfigFactory.parseString("akka.remote.netty.tcp.port="+port)
                .withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.hostname="+host))
                .withFallback(ConfigFactory.load().getConfig("BackEndCluster"));

        ActorSystem actorSystem = ActorSystem.create("transform-system",config);

        final ActorRef actorRef = actorSystem.actorOf(Props.create(Backend.class),"backend");

    }



}
