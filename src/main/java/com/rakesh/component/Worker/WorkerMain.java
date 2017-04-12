package com.rakesh.component.Worker;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.client.ClusterClient;
import akka.cluster.client.ClusterClientSettings;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by ranantoju on 4/11/2017.
 */
public class WorkerMain {
    public static void main(String argv[]) throws InterruptedException {
//        startWorker(0);
        for (int i = 0; i < 300 ; i++) {
            startWorker(0);
        }
    }

    public static void startWorker(int port) {
        Config conf = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
                withFallback(ConfigFactory.load("worker"));

        ActorSystem system = ActorSystem.create("WorkerSystem", conf);

        ActorRef clusterClient = system.actorOf(
                ClusterClient.props(ClusterClientSettings.create(system)),
                "clusterClient");
        system.actorOf(Worker.props(clusterClient, Props.create(JobExecutor.class)), "worker");
    }
}
