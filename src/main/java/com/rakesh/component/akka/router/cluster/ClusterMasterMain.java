package com.rakesh.component.akka.router.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mac on 4/17/17.
 */
public class ClusterMasterMain {

    public static void main(String args[]) {

        Config config = ConfigFactory.load().getConfig("ClusterAware");

        final ActorSystem actorSystem = ActorSystem.create("cluster-aware-system", config);

        final ActorRef master = actorSystem.actorOf(Props.create(ClusterMaster.class), "cluster-master");

        AtomicInteger i = new AtomicInteger(0);
        actorSystem.scheduler().schedule(Duration.create(15, TimeUnit.SECONDS), Duration.create(2, TimeUnit.SECONDS),
                () -> {
                    MessageProcess messageProcess = new MessageProcess("hello, this is work process-" + i.incrementAndGet());
                    master.tell(messageProcess, ActorRef.noSender());
                }, actorSystem.dispatcher());


        actorSystem.scheduler().schedule(Duration.create(15, TimeUnit.SECONDS), Duration.create(2, TimeUnit.SECONDS),
                () -> {
                    MessageProcess messageProcess = new MessageProcess("hello, this is another work process-" + i.incrementAndGet());
                    master.tell(messageProcess, ActorRef.noSender());
                }, actorSystem.dispatcher());

/*

        String[] ports = new String[]{"9001", "9002"};
        for (String port : ports) {

            config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
                    .withFallback(ConfigFactory.parseString("akka.cluster.roles = [worker]"))
                    .withFallback(ConfigFactory.load().getConfig("ClusterAware"));

            final ActorSystem system = ActorSystem.create("cluster-aware-system", config);

            system.actorOf(Props.create(ClusterWorker.class), "cluster-worker");

        }
*/

    }

}
