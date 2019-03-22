package com.bth.io.database.mongodb.mongodbconnector;

import com.bth.gui.controller.DatabaseLoginUser;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.time.LocalDate;

public class MongoDBConnection {
  private final MongoClient client;
  private final MongoDatabase database;
  private final DatabaseLoginUser user;

  public MongoDBConnection(DatabaseLoginUser user) {
    this.user = user;
    MongoClientURI uri = new MongoClientURI(user.getMongoConnectorName());
    client = new MongoClient(uri);
    database = client.getDatabase(user.getMongoDatabaseName());

    System.out.println(this.toString());
  }

  @Override
  public String toString() {
    return "You were connected to a MongoDB Database. " +
        "Info:\nDatabase:"
        + user.getMongoDatabaseName() +
        "\nAs User:" + user.getUserName() +
        "\nAt time:" + LocalDate.now().toString();
  }

  public void disconnect() {
    client.close();
  }

  public MongoClient getClient() {
    return client;
  }

  public DatabaseLoginUser getUser() {
    return user;
  }

  public MongoDatabase getMongoDatabase() {
    return database;
  }

  public boolean isConnected() {
    return client != null && database != null;
  }
}
