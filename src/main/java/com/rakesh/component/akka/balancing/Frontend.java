package com.rakesh.component.akka.balancing;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.cluster.Cluster;
import akka.cluster.singleton.ClusterSingletonProxy;
import akka.cluster.singleton.ClusterSingletonProxySettings;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by mac on 4/19/17.
 */
public class Frontend  extends AbstractActor {


    private Cluster cluster = Cluster.get(getContext().system());

    ActorRef masterProxy = getContext().actorOf(
            ClusterSingletonProxy.props(
                    "/user/master",
                    ClusterSingletonProxySettings.create(getContext().system()).withRole("master")),
            "masterProxy");

    @Override
    public void postStop(){
        cluster.unsubscribe(self());
    }


    public Frontend() {

        receive(ReceiveBuilder
                .match(String.class, msg -> {
                    System.out.println("---got messge from frontned");
                    Work work = new Work(msg);
                    masterProxy.tell(work, self());
                }).build()

        );
    }

}
