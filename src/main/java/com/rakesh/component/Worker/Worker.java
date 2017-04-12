package com.rakesh.component.Worker;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import akka.actor.*;
import akka.cluster.client.ClusterClient;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
//import frontend.FrontendBridger.*;
//import job.JobProtocols.*;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.UUID;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.stop;

/**
 * Created by ranantoju on 4/11/2017.
 */
public class Worker extends AbstractActor {

    private final String workerId = UUID.randomUUID().toString();
    private Cancellable registerTask;
    private ActorRef jobExecutor;
    private ActorRef clusterClient;
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    //    private String jobId = null;
    private final Integer jobLimit = 200;
    private Integer jobRunning = 0;
    //    private Integer jobRequesting = 0;

    public static Props props(ActorRef clusterClient, Props jobExecutorProps, FiniteDuration registerInterval) {
        return Props.create(Worker.class, clusterClient, jobExecutorProps, registerInterval);
    }

    public static Props props(ActorRef clusterClient, Props workExecutorProps) {
        return props(clusterClient, workExecutorProps, Duration.create(10, "seconds"));
    }

    public Worker(ActorRef clusterClient, Props jobExecutorProps, FiniteDuration registerInterval) {
        this.clusterClient = clusterClient;
        this.jobExecutor = getContext().watch(getContext().actorOf(jobExecutorProps, "exec"));
        this.registerTask = getContext().system().scheduler().schedule(Duration.Zero(), registerInterval,
                clusterClient, new ClusterClient.SendToAll("/user/master/singleton", new MasterWorkerProtocols.WorkerRegistryMasterProtocol(workerId)),
                getContext().dispatcher(), self());
    }

    private void sendToMaster(Object msg) {
        clusterClient.tell(new ClusterClient.SendToAll("/user/master/singleton", msg), self());
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(-1, Duration.Inf(),
                new Function<Throwable, SupervisorStrategy.Directive>() {
                    @Override
                    public SupervisorStrategy.Directive apply(Throwable t) {
                        log.info("=====>>> OneForOneStrategy {}.", t);
                        if (t instanceof ActorInitializationException)
                            return stop();
                        else if (t instanceof DeathPactException)
                            return stop();
                        else if (t instanceof Exception) {
                            jobRunning = Math.max(0, jobRunning - 1);
                            if (jobLimit - jobRunning > 0) {
                                sendToMaster(new MasterWorkerProtocols.WorkerRequestJobProtocol(workerId, jobLimit - jobRunning));
                            }
//                            if (jobId != null)
//                                sendToMaster(new WorkerJobFailedProtocol(workerId, jobId()));
//                            getContext().become(idle);
                            return restart();
                        } else {
                            return escalate();
                        }
                    }
                }
        );
    }

    public Worker() {
        receive(ReceiveBuilder.match(String.class, msg -> {
            System.out.println("----------------- I am printing --------------------");
            System.out.println("---------->" + self() + "--->" + msg);
        }).build());
    }

}
