package com.rakesh.component.akka.workerdialin;


import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.japi.pf.ReceiveBuilder;
import akka.remote.transport.ThrottlerTransportAdapter;
import akka.cluster.Member;
import akka.cluster.MemberStatus;

/**
 * Created by ranantoju on 3/25/2017.
 */
public class TransformationBackend extends AbstractActor  {

    Cluster cluster;

    @Override
    public void preStart(){
        cluster = Cluster.get(getContext().system());
    }

    @Override
    public void postStop(){
        cluster.unsubscribe(self());
    }

    public TransformationBackend(){
        receive(ReceiveBuilder
                .match(TransformationMessages.TransformationJob.class,job ->{
                    String jobString = job.getTransformString();
                    String finalString = backendRule(jobString);
                    System.out.println("*****"+finalString);
                    TransformationMessages.TransformationResult transformationResult =
                            new TransformationMessages.TransformationResult(finalString);
                    sender().tell(transformationResult,self());
                })
                .match(ClusterEvent.CurrentClusterState.class, state -> {
                    for(Member member : state.getMembers()){
                        if (member.status().equals(MemberStatus.up())) {
                            register(member);
                        }
                    }
                })
                .match(ClusterEvent.MemberUp.class, mUp -> {
                    register(mUp.member());
                })
                .build()
        );
    }

    public String backendRule(String jobString){
        return jobString.toUpperCase();
    }

    public void register(Member member){
        if(member.hasRole("frontend")){
            getContext().actorSelection(member.address()+"/user/frontend").tell("BackendWorkers",self());
        }
    }
}
