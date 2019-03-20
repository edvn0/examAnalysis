package com.bth.io.database.sql.sqlconnector;

import com.bth.gui.controller.DatabaseLoginUser;
import com.bth.io.database.sql.information.SQLDBInformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class SQLConnector
{
  public static DatabaseLoginUser databaseLoginUser;

  public static Connection connectToDatabase()
  {
    Connection connection = null;
    System.out.println("We are here trying to connect to the database.");

    String url = SQLDBInformation.url == null ? databaseLoginUser.getDatabase() : SQLDBInformation.url;
    String user = SQLDBInformation.user == null ? databaseLoginUser.getUser() : SQLDBInformation.user;
    String pass = SQLDBInformation.pass == null ? databaseLoginUser.getPassword() : SQLDBInformation.pass;

    try (Connection connection1 = DriverManager.getConnection(url, user, pass))
    {
      System.out.println(url + " " + user + " " + pass);

      connection = connection1;

      System.out.println(String.format("You are now connected to the database [ %s ] as user %s, at %s", url.split("/")[2], user, LocalDate.now().toString()));
      System.out.println(connection.isClosed());
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
    return connection;
  }


}
