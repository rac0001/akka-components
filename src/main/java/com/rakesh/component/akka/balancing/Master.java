package com.rakesh.component.akka.balancing;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.cluster.Cluster;
import akka.japi.pf.ReceiveBuilder;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by ranantoju on 4/8/2017.
 */
public class Master extends AbstractActor{

    private Cluster cluster = Cluster.get(getContext().system());

    private Map<ActorRef,Optional<WorkStat>> actorRefMap = new LinkedHashMap<>();
    private Queue<WorkStat> workQueue = new ArrayBlockingQueue<WorkStat>(1000);

    @Override
    public void postStop(){
        cluster.unsubscribe(self());
    }

    public Master() {
        receive(ReceiveBuilder
                .match(Work.class, work -> {
                            WorkStat workStat = new WorkStat(work, self());
                            boolean added = workQueue.offer(workStat);
                            if(!added){
                                workQueue.offer(workStat);
                            }
                            reportToWorker();
                        })
                .match(MessageHandler.InitializeWorkers.class, init -> {
                    ActorRef workerRef = init.getWorker();
                    actorRefMap.put(workerRef,Optional.empty());
                    context().watch(workerRef);
                    reportToWorker();
                })
                .match(MessageHandler.RequestWork.class, reqWork ->{
                    if(actorRefMap.containsKey(reqWork.getWorker())){
                        if(workQueue.isEmpty())
                            reqWork.getWorker().tell(new MessageHandler.NoWork(),self());
                        else if(! actorRefMap.get(reqWork.getWorker()).isPresent()){
                            WorkStat workStat = workQueue.poll();
                            actorRefMap.put(reqWork.getWorker(),Optional.of(workStat));
                            reqWork.getWorker().tell(new MessageHandler.DoWork(workStat.getWork()),workStat.getSender());
                        }
                    }
                })
                .match(MessageHandler.WorkIsDone.class , workCom -> {
                    if(!actorRefMap.containsKey(workCom.getWorker())) {
                        //oh, but why no worker added before
                    }else{
                        actorRefMap.put(workCom.getWorker(),Optional.empty());
                    }
                })
                .build()
        );
    }


    public void reportToWorker(){
        if(! workQueue.isEmpty()){
            actorRefMap.forEach( (actor,workStat) -> {
                if(! workStat.isPresent()) {
                    actor.tell(new MessageHandler.WorkReady(), self());
                }
            });
        }
    }

}
