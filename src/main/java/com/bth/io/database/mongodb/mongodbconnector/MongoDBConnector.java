package com.bth.io.database.mongodb.mongodbconnector;

import com.bth.gui.controller.DatabaseLoginUser;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnector {
  public static DatabaseLoginUser user;
  private static MongoClientURI uri;
  private static MongoClient client;
  private static MongoDatabase database;

  MongoDBConnector() {
    uri = null;
    client = null;
    database = null;
  }

  public static MongoCollection<BasicDBObject> connectToMongoDB(DatabaseLoginUser user) {
    uri = new MongoClientURI(user.getDatabase());
    client = new MongoClient(uri);
    database = client.getDatabase(user.getMongoDatabase());
    return database.getCollection(user.getCollection(),
        BasicDBObject.class);
  }

  public static void disconnect() {
    if (client != null) client.close();
  }

  public static boolean isConnected() {
    return (client != null);
  }
}
