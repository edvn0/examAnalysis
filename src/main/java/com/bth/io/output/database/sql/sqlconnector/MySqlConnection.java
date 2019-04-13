package com.bth.io.output.database.sql.sqlconnector;

import com.bth.gui.controller.loginusers.DatabaseLoginUser;
import com.bth.gui.controller.loginusers.SQLLoginUser;
import com.bth.io.output.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class MySqlConnection extends DatabaseConnection {

  private SQLLoginUser user;
  private Connection connection;

  public MySqlConnection(DatabaseLoginUser user) {
    this.user = (SQLLoginUser) user;
  }

  @Override
  public String toString() {
    return "You were connected to a MySQL Database. " +
        "\nInfo:\nDatabase:"
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
      connection = null;
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean validateDatabase(DatabaseLoginUser user) {
    return user.getConnector().startsWith("jdbc:mysql://");
  }

  public void connect(DatabaseLoginUser user) {
    Connection connection = null;
    if (!isUserValidated(user)) {
      System.out.println("User could not be validated. Try again.");
    } else {
      try {
        connection = DriverManager
            .getConnection(user.getConnector(), user.getUserName(), user.getPassword());
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    this.connection = connection;
  }

  public boolean isUserValidated(DatabaseLoginUser user) {
    return user.validateUser(user.getUserName(), user.getPassword().toCharArray());
  }
}
