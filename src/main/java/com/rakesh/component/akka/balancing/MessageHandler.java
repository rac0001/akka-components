package com.rakesh.component.akka.balancing;

import akka.actor.ActorRef;

import java.io.Serializable;

/**
 * Created by mac on 4/15/17.
 */
public class MessageHandler implements Serializable{

    public static class InitializeWorkers implements Serializable{
        ActorRef worker;
        InitializeWorkers(ActorRef worker){
            this.worker = worker;
        }
        public ActorRef getWorker() {
            return worker;
        }
    }

    public static class RequestWork implements Serializable{
        ActorRef worker;
        RequestWork(ActorRef worker){
            this.worker = worker;
        }
        public ActorRef getWorker() {
            return worker;
        }
    }

    public static class WorkIsDone implements Serializable{
        ActorRef worker;
        WorkIsDone(ActorRef worker){
            this.worker = worker;
        }
        public ActorRef getWorker() {
            return worker;
        }
    }

    public static class DoWork implements Serializable{
        Work work;
        DoWork(Work work){
            this.work = work;
        }
        public Work getWork() {
            return work;
        }
    }

    public static class WorkReady implements Serializable{}
    public static class NoWork implements Serializable{}
    public static class WorkCompleted implements Serializable{}

}
