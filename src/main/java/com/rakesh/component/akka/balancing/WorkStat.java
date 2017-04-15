package com.rakesh.component.akka.balancing;

import akka.actor.ActorRef;

import java.io.Serializable;

/**
 * Created by mac on 4/15/17.
 */
public class WorkStat implements Serializable{

    private Work work;
    private ActorRef sender;

    public WorkStat(Work work, ActorRef sender) {
        this.work = work;
        this.sender = sender;
    }

    public Work getWork() {
        return work;
    }

    public ActorRef getSender() {
        return sender;
    }
}
