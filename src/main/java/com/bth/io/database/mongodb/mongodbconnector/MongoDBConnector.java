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

  public static void connectToMongoDB() {

    uri = new MongoClientURI(user.getMongoConnectorName());
    client = new MongoClient(uri);
    database = client.getDatabase(user.getMongoDatabaseName());
  }

  public static MongoCollection<BasicDBObject> getCollectionFromInputCollection(String inputCollection) {
    connectToMongoDB();
    return database.getCollection(inputCollection, BasicDBObject.class);
  }

  public static void disconnect() {
    if (client != null) client.close();
  }

  public static boolean isConnected() {
    return (client != null);
  }
}
