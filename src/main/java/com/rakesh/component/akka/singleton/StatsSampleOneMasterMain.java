package com.rakesh.component.akka.singleton;

import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import akka.cluster.singleton.ClusterSingletonProxy;
import akka.cluster.singleton.ClusterSingletonProxySettings;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by ranantoju on 4/12/2017.
 */
public class StatsSampleOneMasterMain {

    public static void main(String[] args) {
        if (args.length == 0) {
            startup(new String[] { "2551", "2552", "0" });
//            StatsSampleOneMasterClientMain.main(new String[0]);
        } else {
            startup(args);
        }
    }

    public static void startup(String[] ports) {
        for (String port : ports) {
            // Override the configuration of the port
            Config config = ConfigFactory
                    .parseString("akka.remote.netty.tcp.port=" + port)
                    .withFallback(
                            ConfigFactory.parseString("akka.cluster.roles = [compute]"))
                    .withFallback(ConfigFactory.load("stats2"));

            ActorSystem system = ActorSystem.create("ClusterSystem", config);

            ClusterSingletonManagerSettings settings = ClusterSingletonManagerSettings.create(system).withRole("compute");

//            system.actorOf(ClusterSingletonManager.props(
//                    Props.create(StatsService.class), PoisonPill.getInstance(), settings), "statsService");

            ClusterSingletonProxySettings proxySettings = ClusterSingletonProxySettings.create(system).withRole("compute");

            system.actorOf(ClusterSingletonProxy.props("/user/statsService", proxySettings), "statsServiceProxy");
        }

    }
}
