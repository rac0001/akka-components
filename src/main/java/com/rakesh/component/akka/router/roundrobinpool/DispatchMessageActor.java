package com.rakesh.component.akka.router.roundrobinpool;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

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

        IntStream.rangeClosed(1,5).forEach( i-> {
            ActorRef actor = getContext().actorOf(Props.create(Worker.class),"actor-"+i);
            routees.add(new ActorRefRoutee(actor));
        });

        router = new Router(new RoundRobinRoutingLogic(),routees);

        ActorRef routerRef = getContext().actorOf(new RoundRobinPool(5).props(Props.create(Worker.class)),"router-2");

//        Config config = ConfigFactory.load().getConfig("router-example");
        ActorRef configRef = getContext().actorOf(FromConfig.getInstance().props(Props.create(Worker.class)),"random-router-pool");


        receive(ReceiveBuilder.match(
                String.class,msg -> {
//                    router.route(msg,self());
//                    routerRef.tell(msg,self());
                    configRef.tell(msg,self());
                }
                ).match(PoisonPill.class,msg->{
                    getContext().stop(self());
                }).build()
        );

    }


}
