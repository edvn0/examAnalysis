package com.bth.io.output.database.mongodb.mongodbconnector;

import com.bth.gui.controller.loginusers.MongoDBUser;
import com.bth.gui.controller.loginusers.SQLLoginUser;
import com.bth.io.output.database.DatabaseConnection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import java.sql.Connection;
import java.time.LocalDate;

public class MongoDBConnection extends DatabaseConnection {

  private MongoClient client;
  private MongoDBUser user;
  private MongoDatabase database;

  public MongoDBConnection(MongoDBUser user) {
    database = connectToMongo(user);
  }

  @Override
  public String toString() {
    return "You were connected to a MongoDB Database. " +
        "Info:\nDatabase: "
        + user +
        "\nAs user: " + user.getUserName() +
        "\nAt time: " + LocalDate.now().toString();
  }

  public void disconnect() {
    client.close();
  }

  public MongoClient getClient() {
    return client;
  }

  public MongoDBUser getUser() {
    return user;
  }

  public MongoDatabase getMongoDatabase() {
    return database;
  }

  public boolean isConnected() {
    return client != null && database != null;
  }

  //
  @Override
  protected Connection connectToSql(SQLLoginUser user) {
    return null;
  }

  @Override
  protected MongoDatabase connectToMongo(MongoDBUser user) {
    this.user = user;
    MongoClientURI uri = new MongoClientURI(user.getConnector());
    client = new MongoClient(uri);
    database = client.getDatabase(user.getDatabaseName());

    System.out.println(this.toString());

    return database;
  }
}
