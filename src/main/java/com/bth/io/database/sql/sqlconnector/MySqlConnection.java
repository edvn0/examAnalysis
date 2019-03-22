package com.bth.io.database.sql.sqlconnector;

import com.bth.gui.controller.DatabaseLoginUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class MySqlConnection {
  private DatabaseLoginUser user;
  private Connection connection;

  public MySqlConnection(DatabaseLoginUser user) {
    this.user = user;
    try {
      connection = DriverManager.getConnection(user.getSqlConnectorName(), user.getUserName(), user.getPassword());
      System.out.println(this.toString());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String toString() {
    return "You were connected to a MySQL Database. " +
        "Info:\nDatabase:"
        + user.getSqlDatabaseName() +
        "\nAs User:" + user.getUserName() +
        "\nAt time:" + LocalDate.now().toString();
  }

  public Connection getConnection() {
    return connection;
  }

  public DatabaseLoginUser getUser() {
    return user;
  }

  public boolean isConnected() {
    try {
      return connection.isClosed();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public void disconnect() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
