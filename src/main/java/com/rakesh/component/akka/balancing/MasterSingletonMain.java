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
            register(new String[]{"9000","9001"});
        }else{
            singleRegister(args[0],args[1]);
        }
    }

    public static void register(String args[]){

        for(String port: args) {
            singleRegister(port,"127.0.0.1");
        }
    }

    public static void singleRegister(String port,String host){
        Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
                .withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.hostname="+host))
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
