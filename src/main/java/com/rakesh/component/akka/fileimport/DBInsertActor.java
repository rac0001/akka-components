package com.rakesh.component.akka.fileimport;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mac on 3/19/17.
 */
public class DBInsertActor extends AbstractActor {

    public DBInsertActor(final MongoCollection<Document> collection){

        receive(ReceiveBuilder
                .match(String.class,line -> insertIntoDB(line,collection))
                .matchAny(this::unhandled)
                .build()
        );

    }

    private void insertIntoDB(String line,final MongoCollection<Document> collection){

        String[] stuDetails = line.split(",");

        Document stuDocument = new Document("_id",new ObjectId())
                .append("stu_id",stuDetails[0])
                .append("stu_name", stuDetails[1])
                .append("stu_gpa",stuDetails[2])
                .append("stu_grade",stuDetails[3]);

//        collection.insertOne(stuDocument);

        sender().tell(new MessageTerminator(),self());
    }
}
