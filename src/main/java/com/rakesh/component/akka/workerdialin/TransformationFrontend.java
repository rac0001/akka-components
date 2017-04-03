package com.rakesh.component.akka.workerdialin;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranantoju on 3/25/2017.
 */
public class TransformationFrontend extends AbstractActor {

    public List<ActorRef> backendList = new ArrayList<>();
    int jobCounter = 0;

    public TransformationFrontend(){

        receive(ReceiveBuilder
                .match(TransformationMessages.TransformationJob.class, job -> backendList.isEmpty() , job -> {
                    sender().tell(new TransformationMessages.FailedJob("no backend nodes available",job),self());
                })
                /*.match(TransformationMessages.TransformationResult.class, result -> {
                    System.out.println("############3"+result.getTransformResultString());
                })*/
                .match(TransformationMessages.TransformationJob.class, job ->{
                    jobCounter++;
                    backendList.stream().filter(ref -> ref != null).forEach(ref -> ref.forward(job,getContext()));
//                    backendList.get( jobCounter % backendList.size()).forward(job,getContext());
                })
                .matchEquals("BackendWorkers", message -> {
                    getContext().watch(sender());
                    backendList.add(sender());
                })
                .match(Terminated.class,terminated -> {
                    backendList.remove(terminated.getActor());
                })
                .build()
        );
    }

}
