package com.rakesh.component.akka.workerdialin;

import akka.actor.*;
import akka.remote.routing.RemoteRouterConfig;
import akka.routing.RoundRobinPool;

/**
 * Created by mac on 3/26/17.
 */
public class TestRemoteRouting {

    public static void main(String args[]){


        ActorSystem system = ActorSystem.create("ClusterSystem");

        Address addr1 = new Address("akka", "ClusterSystem", "127.0.0.1", 2558);
        Address addr2 = AddressFromURIString.parse("akka.tcp://ClusterSystem@127.0.0.1:2558");
        Address[] addresses = new Address[] { addr2 };
        ActorRef routerRemote = system.actorOf(Props.create(TransformationBackend.class).withRouter(new RemoteRouterConfig(new RoundRobinPool(5), addresses)));

        routerRemote.tell("remote rorr",ActorRef.noSender());


    }

}
