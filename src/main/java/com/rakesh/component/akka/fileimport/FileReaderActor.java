package com.rakesh.component.akka.fileimport;

import akka.actor.*;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by mac on 3/19/17.
 */
public class FileReaderActor extends AbstractActor {

    public static final Logger logger = LoggerFactory.getLogger(FileReaderActor.class);

    private Props fileProps;
    private Router router;
    private MongoCollection<Document> dbCollection;
    private AtomicInteger messageSent = new AtomicInteger(0);
    private int totalLines;

    public FileReaderActor(){

        configureDB();

        configRouter();

        receive(ReceiveBuilder
                .match(String.class, this::processFile)
                .match(MessageTerminator.class,o -> stopFileActor())
                .matchAny(this::unhandled)
                .build());
    }

    private void configRouter(){

        fileProps = Props.create(DBInsertActor.class,dbCollection);

        List<Routee> routees = new ArrayList<>();

        IntStream.rangeClosed(1,10).forEach( i -> {
            ActorRef dbActor = getContext().actorOf(fileProps,"db-actor-"+i);
            routees.add(new ActorRefRoutee(dbActor));
        });

        router = new Router(new RoundRobinRoutingLogic(),routees);

    }


    private void configureDB(){

        MongoClient mongoClient = new MongoClient("localhost", 27017);

        MongoDatabase db = mongoClient.getDatabase("local");

        db.getCollection("studentmarks").drop();

        db.createCollection("studentmarks", new CreateCollectionOptions().autoIndex(false).capped(false));

        dbCollection = db.getCollection("studentmarks");

    }

    private void processFile(String fileName){

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            List<String> finalStream = stream.filter(line -> ! line.startsWith("Id,Name,Gpa,Grade")).collect(Collectors.toList());
            totalLines = finalStream.size();
            finalStream.forEach(this::processEachLineUsingActor);
        } catch (IOException e) {
            logger.error("file reading exception",e);
        }
    }

    private void processEachLineUsingActor(String eachFileLine){
        router.route(eachFileLine,self());
    }

    private void stopFileActor(){
        int totalmessagesSent = messageSent.incrementAndGet();

        if(totalmessagesSent == totalLines){
            logger.info("total messages read,shutting down context");
            getContext().stop(self());
            context().system().shutdown();
        }

    }

}
