package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;

/**
 * Example Mongo drive setup class
 */
public class App {

    public static final int MONGO_DEFAULT_PORT = 27017;
    public static final String DATABASE_NAME = "test";
    public static final String COLLECTION_NAME = "scores";
    public static final String SERVER = "localhost";

    public static void main(String[] args) {
        MongoCollection<Document> scores = getMongoCollection(COLLECTION_NAME);

        Document newStudent = getStudent(12000, "essay", 100);
        //scores.insertOne(newStudent);


        // Can use .append to add more criteria
        Document filterDoc = new Document("student", 12000);
        //scores.updateOne(Filters.eq("student", 12000), new Document("$set", new Document("score", 98)));
        System.out.println("filtered by student = 12000 count: " + scores.count(filterDoc));
        // For each new criteria use append, a new Document is required if there would be one in the BSON.
        filterDoc.append("score", new Document("$gt", 99));
        System.out.println("Student 12000 and also filtered by score > 99 count: " + scores.count(filterDoc));

    }

    private static Document getStudent(int student, String type, int score) {
        return new Document().append("student", student)
                             .append("type", type)
                             .append("score", score);
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
