package com.rakesh.component.akka.balancing;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.rakesh.component.akka.Transformation.AddMessage;
import com.rakesh.component.akka.example2.Checker;
import com.rakesh.component.akka.example2.Storage;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mac on 4/15/17.
 */
public class Main {

    public static void main(String args[]){

        final ActorSystem actorSystem = ActorSystem.create("load-balancing-system");

        final ActorRef master = actorSystem.actorOf(Props.create(Master.class),"master");

        final ActorRef worker1 = actorSystem.actorOf(Props.create(Worker.class,master),"worker-1");
        final ActorRef worker2 = actorSystem.actorOf(Props.create(Worker.class,master),"worker-2");
        final ActorRef worker3 = actorSystem.actorOf(Props.create(Worker.class,master),"worker-3");
        final ActorRef worker4 = actorSystem.actorOf(Props.create(Worker.class,master),"worker-4");

        ActorRef workResolver = actorSystem.actorOf(Props.create(WorkResolver.class),"worker-resolver");

        AtomicInteger i = new AtomicInteger(0);

        actorSystem.scheduler().schedule(Duration.create(5, TimeUnit.SECONDS),Duration.create(2, TimeUnit.SECONDS),
                () -> {
                    Work work = new Work("hello, this is work-"+i.incrementAndGet());
                    master.tell(work, ActorRef.noSender());
                }, actorSystem.dispatcher());

        master.tell(new MessageHandler.InitializeWorkers(worker1),ActorRef.noSender());
        master.tell(new MessageHandler.InitializeWorkers(worker2),ActorRef.noSender());
        master.tell(new MessageHandler.InitializeWorkers(worker3),ActorRef.noSender());
        master.tell(new MessageHandler.InitializeWorkers(worker4),ActorRef.noSender());


    }


}
