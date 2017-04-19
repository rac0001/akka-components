package com.rakesh.component.akka.router.roundrobinpool;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.*;
import akka.util.Timeout;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by ranantoju on 4/1/2017.
 */
public class DispatchMessageActor extends AbstractActor {

    Router router;

    DispatchMessageActor(){

        List<Routee> routees = new ArrayList<>();

//        IntStream.rangeClosed(1,5).forEach( i-> {
//            ActorRef actor = getContext().actorOf(Props.create(Worker.class),"actor-"+i);
//            routees.add(new ActorRefRoutee(actor));
//        });
//
//        router = new Router(new RoundRobinRoutingLogic(),routees);

//        ActorRef routerRef = getContext().actorOf(new RoundRobinPool(5).props(Props.create(Worker.class)),"router-2");

//        Config config = ConfigFactory.load().getConfig("router-example");
        ActorRef routee = getContext().actorOf(FromConfig.getInstance().props(Props.create(MyBoundedActor.class,getContext().system().settings(),ConfigFactory.load())),"random-router-pool");

//        ActorRef myActor = system.actorOf(Props.create(Demo.class, this)
//                .withDispatcher("prio-dispatcher"));

        receive(ReceiveBuilder.match(
                String.class, (String msg) -> {

                    routee.tell(msg,self());


/*                    Timeout timeout = new Timeout(Duration.create(5, "seconds"));
                    Future<Object> futureResult = akka.pattern.Patterns.ask(
                            routee,msg, timeout);

                    String result = (String) Await.result(futureResult, timeout.duration());
                    System.out.println("---finish result --"+result+"-"+sender());*/
                }
                ).match(Result.class,msg->{
                    System.out.println("---finish--"+sender().path());
                }).build()
        );

    }


}
