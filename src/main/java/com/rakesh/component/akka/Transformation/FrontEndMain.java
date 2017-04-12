package com.rakesh.component.akka.Transformation;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ranantoju on 4/8/2017.
 */
public class FrontEndMain {

    public static void main(String args[]){

        Config config= ConfigFactory.load().getConfig("FrontEndCluster");

        ActorSystem actorSystem = ActorSystem.create("transform-system",config);

        final ActorRef actorRef = actorSystem.actorOf(Props.create(FrontEnd.class),"frontend");


/*
        actorSystem.scheduler().scheduleOnce(Duration.create(60, TimeUnit.SECONDS),
=======
        actorSystem.scheduler().scheduleOnce(Duration.create(30, TimeUnit.SECONDS),
>>>>>>> origin/master
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("$$$$$$$ sending message after 25 sec");
                        actorRef.tell(new AddMessage("hello from frontend"), ActorRef.noSender());
                    }
                }, actorSystem.dispatcher());
*/

//        actorRef.tell(new AddMessage("hello from frontend"), ActorRef.noSender());


        AtomicInteger i = new AtomicInteger(0);
        actorSystem.scheduler().schedule(Duration.create(60, TimeUnit.SECONDS),Duration.create(5, TimeUnit.SECONDS),
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("$$$$$$$ sending message after 5 sec");
                        actorRef.tell(new AddMessage("hello from frontend-"+i.incrementAndGet()), ActorRef.noSender());
                    }
                }, actorSystem.dispatcher());


    }



}
