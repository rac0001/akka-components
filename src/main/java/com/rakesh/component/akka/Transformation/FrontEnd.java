package com.rakesh.component.akka.Transformation;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ranantoju on 4/8/2017.
 */
public class FrontEnd extends AbstractActor {

    private Cluster cluster = Cluster.get(getContext().system());
    private List<ActorRef> backendList = new ArrayList<>();
    int jobCounter = 0;

    @Override
    public void postStop(){
        cluster.unsubscribe(self());
    }

    public FrontEnd() {
        receive(ReceiveBuilder
                .match(AddMessage.class ,msg -> backendList.isEmpty() ,msg -> {
                    System.out.println("backend list is empty");})
                .match(AddMessage.class , msg -> {

                    jobCounter++;
                    backendList.get( jobCounter % backendList.size()).tell(msg.getMsg(),self());

//                    int val = new Random().nextInt(backendList.size());
//                    backendList.get(val).tell(msg.getMsg(),self());

                }).match(String.class ,msg -> msg.equalsIgnoreCase("backendWorker") &&
                        !backendList.contains(sender()), msg -> {
                    System.out.println("adding backend :"+sender().path());
                    backendList.add(sender());
                    getContext().watch(sender());
                }).match(ResultMessage.class , msg -> {
                    System.out.println("transformed backend String:"+msg.getMsg());
                }).match(Terminated.class,msg -> {
                    backendList.remove(msg.getActor());
                })
                .build()
        );
    }

}
