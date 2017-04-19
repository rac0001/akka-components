package com.rakesh.component.akka.router.cluster;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by mac on 4/17/17.
 */
public class ClusterWorkerMain {

    public static void main(String args[]){

        String port = args.length == 0 ? "0" : args[0];
        String host = args.length == 0 ? "127.0.0.1" : args[1];

        Config config= ConfigFactory.parseString("akka.remote.netty.tcp.port="+port)
                .withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.hostname="+host))
                .withFallback(ConfigFactory.parseString("akka.cluster.roles = [worker]"))
                .withFallback(ConfigFactory.load().getConfig("ClusterAware"));

        final ActorSystem actorSystem = ActorSystem.create("cluster-aware-system",config);

        actorSystem.actorOf(Props.create(ClusterWorker.class),"cluster-worker");

    }


}
