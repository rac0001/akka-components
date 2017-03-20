package com.rakesh.component.akka.fileimport;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by mac on 3/18/17.
 */
public class ImportApp {

    public static void main(String args[]){

        String fileName = "src/main/resources/marks.csv";

        if(StringUtils.isBlank(fileName))
            throw new IllegalArgumentException("file missing");

        ActorSystem fileSystem = ActorSystem.create("file-import");

        Props fileProps = Props.create(FileReaderActor.class);

        final ActorRef fileActor = fileSystem.actorOf(fileProps,"file-reader");

        fileActor.tell(fileName, ActorRef.noSender());

    }


}
