package com.rakesh.component.akka.remote.sample;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

import java.io.IOException;

/**
 * Created by mac on 4/2/17.
 */
public class Worker extends AbstractActor {

    Worker(){

        receive(ReceiveBuilder
                .match(String.class, msg -> {
                    System.out.println("received message->"+msg);
                    System.out.println(self().path().toString());
//                    sender().tell("send back to master",self());
                })
                .build()

        );


    }

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
