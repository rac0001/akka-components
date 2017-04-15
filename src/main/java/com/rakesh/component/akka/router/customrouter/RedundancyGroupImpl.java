package com.rakesh.component.akka.router.customrouter;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranantoju on 4/13/2017.
 */
public class RedundancyGroupImpl {

    public static void main(String args[]){

        ActorSystem system = ActorSystem.create("custom-router");

        for (int n = 1; n <= 2; n++) {
            system.actorOf(Props.create(Worker.class), "worker" + n);
        }

        List<String> paths = new ArrayList<>();
        for (int n = 1; n <= 2; n++) {
            paths.add("/user/worker" + n);
        }

        ActorRef redundancy1 = system.actorOf(new RedundancyGroup(paths, 2).props(),"redundancy");

        redundancy1.tell("important1", ActorRef.noSender());
        redundancy1.tell("important2", ActorRef.noSender());
        redundancy1.tell("important3", ActorRef.noSender());
        redundancy1.tell("important4", ActorRef.noSender());
        redundancy1.tell("important5", ActorRef.noSender());


    }

}
