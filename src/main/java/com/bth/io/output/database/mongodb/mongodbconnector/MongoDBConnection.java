package com.bth.io.output.database.mongodb.mongodbconnector;

import com.bth.gui.controller.loginusers.DatabaseLoginUser;
import com.bth.gui.controller.loginusers.MongoDBUser;
import com.bth.io.output.database.ConnectInterface;
import com.bth.io.output.database.DatabaseConnection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDate;

public class MongoDBConnection extends DatabaseConnection implements ConnectInterface {

  private MongoClient client;
  private MongoDBUser user;
  private MongoDatabase database;

  public MongoDBConnection(MongoDBUser user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "You were connected to a MongoDB Database. " +
        "Info:\nDatabase: "
        + user +
        "\nAs user: " + user.getUserName() +
        "\nAt time: " + LocalDate.now().toString();
  }

  @Override
  public void connect(DatabaseLoginUser u) {
    MongoClientURI uri = new MongoClientURI(u.getConnector());
    client = new MongoClient(uri);
    this.database = client.getDatabase(u.getDatabaseName());

    System.out.println(this.toString());
  }

  public void disconnect() {
    database = null;
    client.close();
  }

  @Override
  public boolean validateDatabase(DatabaseLoginUser connection) {
    return connection.getConnector().startsWith("mongodb+srv:");
  }

  @Override
  public boolean isUserValidated(DatabaseLoginUser user) {
    return user.validateUser(user.getUserName(), user.getPassword().toCharArray());
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
    if (client.getCredential().getUserName() == null) {
      return false;
    }

    return (client != null && database != null) && database.getName() != null
        && client.getCredential().getUserName() != null
        && client.getCredential().getPassword() != null;
  }
}
