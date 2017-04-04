package com.rakesh.component.akka.router.roundrobingroup;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.*;
import com.rakesh.component.akka.router.roundrobinpool.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by ranantoju on 4/1/2017.
 */
public class DispatchMessageActor extends AbstractActor {


    DispatchMessageActor(){

        List<String> routees = new ArrayList<>();

        routees.add("/user/worker-1");
        routees.add("/user/worker-2");
        routees.add("/user/worker-3");

        ActorRef router = getContext().actorOf(new RoundRobinGroup(routees).props(),"group");

        receive(ReceiveBuilder.match(
                String.class,msg -> {
                    router.tell(msg,self());
                }
                ).match(PoisonPill.class,msg->{
                    getContext().stop(self());
                }).build()
        );

    }


}
