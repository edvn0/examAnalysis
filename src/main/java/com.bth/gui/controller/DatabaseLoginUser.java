package com.bth.gui.controller;

import java.sql.SQLException;

public class DatabaseLoginUser
{
  private String database, user, password, collection, typeDatabase;

  public DatabaseLoginUser(String typeDatabase, String database, String user, char[] pass, String collection) throws SQLException
  {
    this.database = validateDatabase(database);
    this.collection = collection;
    this.typeDatabase = typeDatabase;

    if (validateUser(String.copyValueOf(pass), user))
    {
      this.password = String.copyValueOf(pass);
      this.user = user;
    } else
    {
      this.password = null;
      this.user = null;
    }
  }

  // TODO: Validate database names according to MySQL or MongoDB
  private String validateDatabase(String db) throws SQLException
  {
    boolean startsWithMYSQL = db.startsWith("jdbc:mysql://");
    boolean startsWithMongoDB = db.startsWith("mongodb+srv://");
    if (!startsWithMYSQL && !startsWithMongoDB)
    {
      throw new SQLException("Database is not validated.");
    }

    return db;
  }

  private boolean validateUser(String password, String user)
  {
    if (password != null && user != null)
    {
      return password.length() > 0 && password.length() < 16 && user.length() > 0 && user.length() < 16;
    }
    return false;
  }


  public String getDatabase()
  {
    return database;
  }

  public String getUser()
  {
    return user;
  }

  public String getPassword()
  {
    return password;
  }

  public String getCollection()
  {
    return collection;
  }

  public String getTypeDatabase()
  {
    return typeDatabase;
  }
}
