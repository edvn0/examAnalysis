package com.bth.gui.controller;

public class DatabaseLoginUser {
  private final String mongoDatabase;
  private String database;
  private String user;
  private String password;
  private String collection;
  private String typeDatabase;

  /***
   * Represents the data from the GUI Login.
   * @param mongoDatabase the Database in the MongoDB system
   * @param typeDatabase what type of Database is used? MySQL/MongoDB
   * @param database MySQL database URI
   * @param user name of database user
   * @param pass password of database user
   * @param collection Mongodb collection
   * @throws Exception throws Exception if user cannot be validated.
   */
  public DatabaseLoginUser(String mongoDatabase, String typeDatabase, String database, String user, char[] pass, String collection) throws Exception {
    this.mongoDatabase = mongoDatabase;
    this.database = validateDatabase(database);
    this.collection = collection;
    this.typeDatabase = typeDatabase;

    if (validateUser(String.copyValueOf(pass), user)) {
      this.password = String.copyValueOf(pass);
      this.user = user;
    } else {
      this.password = null;
      this.user = null;
    }
  }

  // TODO: Validate database names according to MySQL or MongoDB
  private String validateDatabase(String db) throws Exception {
    boolean startsWithMySql = db.startsWith("jdbc:mysql://");
    boolean startsWithMongoDb = db.startsWith("mongodb+srv://");
    if (!startsWithMySql && !startsWithMongoDb) {
      String exception = "Database is not validated.";
      throw new Exception(exception);
    }

    return db;
  }

  private boolean validateUser(String password, String user) {
    if (password != null && user != null) {
      return password.length() > 0 && password.length() < 16 && user.length() > 0 && user.length() < 16;
    }
    return false;
  }

  @Override
  public String toString() {
    return "User{" +
        "database='" + database + '\'' +
        ", user='" + user + '\'' +
        ", password='" + password + '\'' +
        ", collection='" + collection + '\'' +
        ", typeDatabase='" + typeDatabase + '\'' +
        '}';
  }

  public String getDatabase() {
    return database;
  }

  public String getUser() {
    return user;
  }

  public String getPassword() {
    return password;
  }

  public String getCollection() {
    return collection;
  }

  public String getTypeDatabase() {
    return typeDatabase;
  }

  public String getMongoDatabase() {
    return mongoDatabase;
  }
}
