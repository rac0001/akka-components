package com.rakesh.component.akka.router.customrouter;

import akka.actor.ActorRef;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.RoutingLogic;
import akka.routing.SeveralRoutees;
import scala.collection.immutable.IndexedSeq;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranantoju on 4/13/2017.
 */
public class RedundancyRoutingLogic implements RoutingLogic {
    private final int nbrCopies;

    public RedundancyRoutingLogic(int nbrCopies) {
        this.nbrCopies = nbrCopies;
    }

    RoundRobinRoutingLogic roundRobin = new RoundRobinRoutingLogic();

    @Override
    public Routee select(Object message, IndexedSeq<Routee> routees) {
//
//        Routee r = routees.apply(1);
//
//        return r;

//        r.send(message, ActorRef.noSender());

        List<Routee> targets = new ArrayList<>();
        for (int i = 0; i < nbrCopies; i++) {
            targets.add(roundRobin.select(message, routees));
        }
        return new SeveralRoutees(targets);
    }
}