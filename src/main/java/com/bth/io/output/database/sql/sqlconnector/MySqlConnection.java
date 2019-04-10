package com.bth.io.output.database.sql.sqlconnector;

import com.bth.gui.controller.loginusers.MongoDBUser;
import com.bth.gui.controller.loginusers.SQLLoginUser;
import com.bth.io.output.database.DatabaseConnection;
import com.mongodb.client.MongoDatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class MySqlConnection extends DatabaseConnection {

  private SQLLoginUser user;
  private Connection connection;

  public MySqlConnection(SQLLoginUser user) {
    this.user = user;
    connection = connectToSql(this.user);
  }

  @Override
  public String toString() {
    return "You were connected to a MySQL Database. " +
        "Info:\nDatabase:"
        + user.getDatabaseName() +
        "\nAs User:" + user.getUserName() +
        "\nAt time:" + LocalDate.now().toString();
  }

  public Connection getConnection() {
    return connection;
  }

  public SQLLoginUser getUser() {
    return user;
  }

  public boolean isConnected() {
    if (connection == null) {
      return false;
    }
    try {
      return connection.isClosed();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return true;
  }

  public void disconnect() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Connection connectToSql(SQLLoginUser user) {
    Connection connection = null;
    try {
      connection = DriverManager
          .getConnection(user.getConnector(), user.getUserName(), user.getPassword());
      System.out.println(this.toString());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }

  @Override
  protected MongoDatabase connectToMongo(MongoDBUser user) {
    return null;
  }
}
