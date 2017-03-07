package com.rakesh.component.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mac on 3/7/17.
 */
public class MongoDBClient {

    public static void main(String args[]){

        MongoClient mongoClient = new MongoClient("localhost", 27017);

        MongoDatabase db = mongoClient.getDatabase("local");
        MongoCollection<Document> collection = db.getCollection("test");

        Document doc1 = new Document("name", "Amarcord Pizzeria")
                .append("contact", new Document("phone", "264-555-0193")
                        .append("email", "amarcord.pizzeria@example.net")
                        .append("location",Arrays.asList(-73.88502, 40.749556)))
                .append("stars", 2)
                .append("categories", Arrays.asList("Pizzeria", "Italian", "Pasta"));


        Document doc2 = new Document("name", "Blue Coffee Bar")
                .append("contact", new Document("phone", "604-555-0102")
                        .append("email", "bluecoffeebar@example.com")
                        .append("location",Arrays.asList(-73.97902, 40.8479556)))
                .append("stars", 5)
                .append("categories", Arrays.asList("Coffee", "Pastries"));

        List<Document> documents = new ArrayList<Document>();
        documents.add(doc1);
        documents.add(doc2);

        collection.insertMany(documents);

        mongoClient.close();



    }


}
