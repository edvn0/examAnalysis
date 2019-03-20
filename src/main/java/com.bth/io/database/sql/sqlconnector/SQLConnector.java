package com.bth.io.database.sql.sqlconnector;

import com.bth.io.database.sql.information.SQLDBInformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class SQLConnector
{
  private static Connection connection;

  public static Connection connectToDatabase()
  {
    try
    {
      String url = SQLDBInformation.url;
      String user = SQLDBInformation.user;
      String pass = SQLDBInformation.pass;
      connection = DriverManager.getConnection(url, user, pass);

      System.out.println(String.format("You are now connected to the database [ %s ] as user %s, at %s", url.split("/")[2], user, LocalDate.now().toString()));
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
    return connection;
  }
}
