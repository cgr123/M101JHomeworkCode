package com.mongodb.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

import static com.mongodb.MongoConstants.CONNECTION_POOL_SIZE;
import static com.mongodb.MongoConstants.MONGO_DEFAULT_PORT;
import static com.mongodb.MongoConstants.SERVER;

/**
 * Class to provide utility function for getting the MongoDb database client object.
 */
public class MongoDbClientFactory {

    public static MongoClient getMongoClient() {
        // Don't need to supply the options bu useful to override defaults e.g. default connections is 100
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(CONNECTION_POOL_SIZE).build();
        // MongoClient is a connection pool, this is a heavy object so often store instance in a singleton or in Spring (as a singleton bean)
        return new MongoClient(new ServerAddress(SERVER, MONGO_DEFAULT_PORT), options);
    }
}
