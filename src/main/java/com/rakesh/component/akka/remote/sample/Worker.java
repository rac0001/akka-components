package com.rakesh.component.akka.remote.sample;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mac on 4/2/17.
 */
public class Worker extends AbstractActor {

    Worker(){

        receive(ReceiveBuilder
                .match(String.class, msg -> {

                    System.out.println("----------got message------"+msg+",path-:"+self().path().toString());

                    sender().tell("Helllo back from worker",self());

/*
                    getContext().system().scheduler().scheduleOnce(Duration.create(15, TimeUnit.SECONDS),
                            () -> System.out.println("received message->"+msg+"--path--"+self().path().toString())
                              , getContext().system().dispatcher());
*/

//                    System.out.println("received message->"+msg);
//                    System.out.println(self().path().toString());
//                    sender().tell("send back to master",self());
                })
                .match(Get.class,name->{
                    System.out.println("----------got message------"+name.getName()+",path-:"+self().path().toString());

                    sender().tell("Helllo back from worker",self());

                })
                .build()

        );


    }

    public static class Get implements Serializable {
        String name;
        Get(String name){
            this.name = name;
        }
        public String getName(){
            return name;
        }
    };

    public static void runScript(String command){
        int iExitValue;
        String sCommandString;

        sCommandString = command;
        CommandLine oCmdLine = CommandLine.parse(sCommandString);
        DefaultExecutor oDefaultExecutor = new DefaultExecutor();
        oDefaultExecutor.setExitValue(0);
        try {
            iExitValue = oDefaultExecutor.execute(oCmdLine);
        } catch (ExecuteException e) {
            System.err.println("Execution failed.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("permission denied.");
            e.printStackTrace();
        }
    }



}
