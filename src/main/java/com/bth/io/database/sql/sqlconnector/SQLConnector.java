package com.bth.io.database.sql.sqlconnector;

import com.bth.gui.controller.DatabaseLoginUser;
import com.bth.io.database.sql.information.SQLDBInformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class SQLConnector {
  public static DatabaseLoginUser databaseLoginUser;
  public static Connection connection;

  SQLConnector() {
    connection = null;
  }

  public static Connection connectToDatabase() {
    Connection connection = null;
    System.out.println("We are here trying to connect to the database.");

    String url = SQLDBInformation.url == null ? databaseLoginUser.getSqlConnectorName() : SQLDBInformation.url;
    String user = SQLDBInformation.user == null ? databaseLoginUser.getUserName() : SQLDBInformation.user;
    String pass = SQLDBInformation.pass == null ? databaseLoginUser.getPassword() : SQLDBInformation.pass;

    try {
      connection = DriverManager.getConnection(url, user, pass);

      System.out.println(String.format("You are now connected to the database [ %s ] as user %s, at %s", url.split("/")[2], user, LocalDate.now().toString()));
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(e.getSQLState());
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return connection;
  }

  public static boolean isConnected() {
    try {
      return (connection != null && !connection.isClosed());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static void disconnect() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}

