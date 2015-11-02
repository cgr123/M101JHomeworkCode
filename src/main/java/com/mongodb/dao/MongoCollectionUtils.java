package com.mongodb.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Class to provide utility methods for MongoDb collections
 */
public class MongoCollectionUtils {

    public static MongoCollection<Document> getCollection(MongoClient client, String databaseName, String collectionName) {
        // MongoDatabase is a lightweight immutable class so no problem creating several of these.
        MongoDatabase db = client.getDatabase(databaseName);
        // Need a collection object to manipulate data (CRUD) this is immutable
        return db.getCollection(collectionName);
    }
}
