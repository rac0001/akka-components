package com.rakesh.component.akka.balancing;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by ranantoju on 4/8/2017.
 */
public class WorkerMain {

    public static void main(String args[]){

        String port = args.length == 0 ? "47576" : args[0];
        String host = args.length == 0 ? "10.248.66.68" : args[1];

        Config config= ConfigFactory.parseString("akka.remote.netty.tcp.port="+port)
                .withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.hostname="+host))
//                .withFallback(ConfigFactory.parseString("akka.cluster.roles = [worker]"))
                .withFallback(ConfigFactory.load().getConfig("BackEndCluster"));

        ActorSystem actorSystem = ActorSystem.create("load-balancing-system",config);

        final ActorRef workProcessor = actorSystem.actorOf(Props.create(WorkProcessor.class),"work-processor");

        final ActorRef worker = actorSystem.actorOf(Props.create(Worker.class,workProcessor),"worker");

    }



}
