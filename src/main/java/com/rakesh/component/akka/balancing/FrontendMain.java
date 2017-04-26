package com.rakesh.component.akka.balancing;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.singleton.ClusterSingletonProxy;
import akka.cluster.singleton.ClusterSingletonProxySettings;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mac on 4/19/17.
 */
public class FrontendMain {

    public static void main(String args[]){

        String port = args.length == 0 ? "8000" : args[0];
        String host = args[1];//"127.0.0.1";

        Config config= ConfigFactory.parseString("akka.remote.netty.tcp.port="+port)
                .withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.hostname="+host))
                .withFallback(ConfigFactory.parseString("akka.cluster.roles = [frontend]"))
                .withFallback(ConfigFactory.load().getConfig("loadbalancer"));

        ActorSystem actorSystem = ActorSystem.create("load-balancing-system",config);

        final ActorRef frontEnd = actorSystem.actorOf(Props.create(Frontend.class),"frontend");

/*
        AtomicInteger i = new AtomicInteger(0);
        actorSystem.scheduler().schedule(Duration.create(15, TimeUnit.SECONDS),Duration.create(5, TimeUnit.SECONDS),
                () -> {
                    frontEnd.tell("hello, this is work-"+i.incrementAndGet(), ActorRef.noSender());
                }, actorSystem.dispatcher());
*/


    }

}
