package com.rakesh.component.akka.router.roundrobingroup;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.*;
import com.rakesh.component.akka.remote.sample.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by ranantoju on 4/1/2017.
 */
public class DispatchMessageActor extends AbstractActor {


    DispatchMessageActor(){

        List<String> routees = new ArrayList<>();


        ActorRef configRef = getContext().actorOf(FromConfig.getInstance().props(Props.create(Worker.class)),"remote-round-robin");



        receive(ReceiveBuilder.match(
                String.class,msg -> {
//                    System.out.println("---->"+msg+"-"+sender());
//                    router.route(msg,self());
//                    routerRef.tell(msg,self());
                    configRef.tell(msg,self());
                }
                ).match(PoisonPill.class,msg->{
                    getContext().stop(self());
                }).build()
        );

/*        routees.add("akka.tcp://remote-system@127.0.0.1:1550");
        routees.add("akka.tcp://remote-system-1@127.0.0.1:1559");
//        routees.add("/user/worker-3");

        ActorRef router = getContext().actorOf(new RoundRobinGroup(routees).props(),"group");

        receive(ReceiveBuilder.match(
                String.class,msg -> {
                    router.tell(msg,self());
                }
                ).match(PoisonPill.class,msg->{
                    getContext().stop(self());
                }).build()
        );*/

    }


}
