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
      System.out.println("You were connected to MySQL. Database: " + user.getSqlDatabaseName() + " at time:" + LocalDate.now().toString());
    } catch (SQLException e) {
      e.printStackTrace();
    }
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
