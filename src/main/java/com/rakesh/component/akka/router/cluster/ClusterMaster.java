package com.rakesh.component.akka.router.cluster;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.FromConfig;

/**
 * Created by mac on 4/17/17.
 */
public class ClusterMaster extends AbstractActor {

    private Cluster cluster = Cluster.get(getContext().system());

    @Override
    public void postStop(){
        cluster.unsubscribe(self());
    }

    ActorRef workerRouter = getContext().actorOf(
            FromConfig.getInstance().props(Props.create(ClusterWorker.class)), "workerRouter");

    ClusterMaster(){

        receive(ReceiveBuilder
                .match(MessageProcess.class , msg -> {
                    workerRouter.tell(msg,self());
                })
                .match(WorkComplete.class , msg ->{
                    System.out.println("--work complete--"+msg.getMessage());
                })
                .build()
        );

    }

}
