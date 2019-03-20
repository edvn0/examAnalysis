package com.bth.io.database.mongodb.mongodbconnector;

import com.bth.gui.controller.DatabaseLoginUser;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnector {
  public static DatabaseLoginUser user;

  public static MongoCollection<BasicDBObject> connectToMongoDB(DatabaseLoginUser user) {
    MongoClientURI uri = new MongoClientURI(user.getDatabase());
    MongoClient client = new MongoClient(uri);
    MongoDatabase database = client.getDatabase(user.getMongoDatabase());
    return database.getCollection(user.getCollection(),
        BasicDBObject.class);
  }
}
