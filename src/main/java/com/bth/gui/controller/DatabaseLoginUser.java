package com.bth.gui.controller;

import java.util.Arrays;

public class DatabaseLoginUser {

  private String schoolCollection;
  private String userName;
  private String password;
  private String choice;
  private String sqlConnectorName;
  private String sqlDatabaseName;
  private String teamCollection;
  private String questionCollection;
  private String schoolTable;
  private String teamTable;
  private String questionTable;
  private String mongoConnectorName;
  private String mongoDatabaseName;

  /***
   * <p>The user who wants to log into the databases.
   * Might be able to reduce this someday...</p>
   * @param userName {String} User Name
   * @param password {char[]} password
   * @param choice choice of database, mysql or mongo
   * @param sqlConnectorName sql connector string
   * @param sqlDatabaseName sql db name
   * @param schoolTable table for school stats in sqldb
   * @param teamTable table for team stats in sqldb
   * @param questionTable table for question statbs in sqldb
   * @param mongoConnectorName mongodb connector string
   * @param mongoDatabaseName mongodb db name
   * @param schoolCollection mongodb school collection for stats
   * @param teamCollection mongodb team collection for stats
   * @param questionCollection mongodb question collection for stats
   */
  public DatabaseLoginUser(String userName, char[] password, String choice,
      String sqlConnectorName, String sqlDatabaseName, String schoolTable,
      String teamTable, String questionTable, String mongoConnectorName,
      String mongoDatabaseName, String schoolCollection,
      String teamCollection, String questionCollection) {

    boolean validatedUser = false;
    try {
      validatedUser = validateUser(userName, password);
    } catch (InstantiationException e) {
      e.printStackTrace();
    }

    if (validatedUser) {
      this.userName = userName;
      this.password = String.copyValueOf(password);
    }

    this.choice = choice;

    try {
      this.sqlConnectorName = validateDatabase(sqlConnectorName);
      this.mongoConnectorName = validateDatabase(mongoConnectorName);
    } catch (Exception e) {
      e.printStackTrace();
    }

    this.sqlDatabaseName = sqlDatabaseName;
    this.mongoDatabaseName = mongoDatabaseName;

    this.schoolTable = schoolTable;
    this.teamTable = teamTable;
    this.questionTable = questionTable;

    this.teamCollection = teamCollection;
    this.questionCollection = questionCollection;
    this.schoolCollection = schoolCollection;
  }

  private String validateDatabase(String db) throws Exception {
    boolean startsWithMySql = db.startsWith("jdbc:mysql://");
    boolean startsWithMongoDb = db.startsWith("mongodb+srv://");
    if (!startsWithMySql && !startsWithMongoDb) {
      String exception = "Database is not validated.";
      throw new Exception(exception);
    }

    return db;
  }

  private boolean validateUser(String password, char[] user) throws InstantiationException {
    if (password != null && user != null) {
      if (password.length() > 0 && password.length() < 16 && user.length > 0 && user.length < 16) {
        return true;
      }
    }
    throw new InstantiationException("Cannot validate user.");
  }

  @Override
  public String toString() {
    return "DatabaseLoginUser{" + "choice='" + choice + ", collectionNames=" + '\'' + Arrays
        .toString(getCollectionNames()) + ", mongoConnectorName='" + mongoConnectorName + '\''
        + ", mongoDatabaseName='" + mongoDatabaseName + '\'' + ", password='" + password + '\''
        + ", questionCollection='" + questionCollection + '\'' + ", questionTable='" + questionTable
        + '\'' + ", schoolCollection='" + schoolCollection + '\'' + ", schoolTable='" + schoolTable
        + '\'' + ", sqlConnectorName='" + sqlConnectorName + '\'' + ", sqlDatabaseName='"
        + sqlDatabaseName + '\'' + ", tableNames=" + Arrays.toString(getTableNames())
        + ", teamCollection='" + teamCollection + '\'' + ", teamTable='" + teamTable + '\''
        + ", userName='" + userName + '\'' + '}';
  }

  public String getPassword() {
    return password;
  }

  public String getSchoolTable() {
    return schoolTable;
  }

  public String getTeamTable() {
    return teamTable;
  }

  public String getQuestionTable() {
    return questionTable;
  }

  public String getSchoolCollection() {
    return schoolCollection;
  }

  public String getUserName() {
    return userName;
  }

  public String getChoice() {
    return choice;
  }

  public String getSqlConnectorName() {
    return sqlConnectorName;
  }

  public String getSqlDatabaseName() {
    return sqlDatabaseName;
  }

  public String getTeamCollection() {
    return teamCollection;
  }

  public String getQuestionCollection() {
    return questionCollection;
  }

  public String getMongoConnectorName() {
    return mongoConnectorName;
  }

  public String getMongoDatabaseName() {
    return mongoDatabaseName;
  }

  private String[] getTableNames() {
    return new String[]{schoolTable, teamTable, questionTable};
  }

  private String[] getCollectionNames() {
    return new String[]{schoolCollection, teamCollection, questionCollection};
  }
}
