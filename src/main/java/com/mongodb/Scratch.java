package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Scratchpad
 */
public class Scratch {
    public static final int MONGO_DEFAULT_PORT = 27017;
    public static final String DATABASE_NAME = "test";
    public static final String COLLECTION_NAME = "playGround";
    public static final String SERVER = "localhost";

    public static void main(String[] args) {
        run();
    }

    private static void run() {

        MongoCollection<Document> playGround = getMongoCollection(COLLECTION_NAME);

        List<Document> doc = playGround.find().sort(new Document("value", -1)).skip(2).limit(1).into(new LinkedList());

        System.out.println(doc);
    }

    private static MongoCollection<Document> getMongoCollection(String collectionName) {
        // Don't need to supply the options bu useful to override defaults e.g. default connections is 100
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(50).build();
        // MongoClient is a connection pool, this is a heavy object so often store instance in a singleton or in Spring (as a singleton bean)
        MongoClient client = new MongoClient(new ServerAddress(SERVER, MONGO_DEFAULT_PORT), options);
        // MongoDatabase is a lightweight immutable class so no problem creating several of these.
        MongoDatabase db = client.getDatabase(DATABASE_NAME);
        // Need a collection object to manipulate data (CRUD) this is immutable
        return db.getCollection(collectionName);
    }
}
