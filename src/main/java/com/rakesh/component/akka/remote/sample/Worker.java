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

/*
                    ProcessBuilder pb = new ProcessBuilder("sh /est-staging/EST_INGESTION_SCRIPTS/sampleTest.sh");
//                    pb.directory(new File("myDir"));
                    Process p = pb.start();
                    p.waitFor();*/

                    System.out.println("-------START------");
//                    runScript("sh /est-staging/EST_INGESTION_SCRIPTS/sampleTest.sh");

                    runScript("sh /est-staging/EST_INGESTION_SCRIPTS/sampleTest.sh");
                    System.out.println("-------END ------");
//                    System.out.println("path is:"+self().path());
//                    System.out.println("self is:"+self());
                    System.out.println("recieved message:"+msg);
//                    getContext().system().terminate();
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