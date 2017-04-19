package com.rakesh.component.akka.balancing;

import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by mac on 4/19/17.
 */
public class MasterSingletonMain {


    public static void main(String args[]) {

        if(args.length == 0){
            register(args);
        }else{
            singleRegister(args[0]);
        }

    }

    public static void register(String args[]){

        String[] ports = {"9000","9001"};

        for(String port: ports) {

            singleRegister(port);
        }
    }

    public static void singleRegister(String port){
        Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
                .withFallback(ConfigFactory.parseString("akka.cluster.roles = [master]"))
                .withFallback(ConfigFactory.load().getConfig("loadbalancer"));

        ActorSystem actorSystem = ActorSystem.create("load-balancing-system", config);

        actorSystem.actorOf(
                ClusterSingletonManager.props(
                        Props.create(Master.class),
                        PoisonPill.getInstance(),
                        ClusterSingletonManagerSettings.create(actorSystem).withRole("master")
                ), "master");

    }

}
