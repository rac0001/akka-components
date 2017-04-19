package com.rakesh.component.akka.router.custom;

import akka.dispatch.BoundedMessageQueueSemantics;
import akka.dispatch.RequiresMessageQueue;

/**
 * Created by ranantoju on 4/18/2017.
 */
public class MyBoundedActor extends WorkerActor implements RequiresMessageQueue<BoundedMessageQueueSemantics> {
}
