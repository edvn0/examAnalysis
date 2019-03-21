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

  public DatabaseLoginUser(String userName, char[] password, String choice, String sqlConnectorName, String sqlDatabaseName, String schoolTable, String teamTable, String questionTable, String mongoConnectorName, String mongoDatabaseName, String schoolCollection, String teamCollection, String questionCollection) {
    this.userName = userName;
    this.password = String.copyValueOf(password);
    this.choice = choice;

    this.sqlConnectorName = sqlConnectorName;
    this.sqlDatabaseName = sqlDatabaseName;
    this.schoolTable = schoolTable;
    this.teamTable = teamTable;
    this.questionTable = questionTable;

    this.mongoConnectorName = mongoConnectorName;
    this.mongoDatabaseName = mongoDatabaseName;
    this.teamCollection = teamCollection;
    this.questionCollection = questionCollection;
    this.schoolCollection = schoolCollection;
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
    final StringBuilder sb = new StringBuilder("DatabaseLoginUser{");
    sb.append("choice='").append(choice).append('\'');
    sb.append(", collectionNames=").append(Arrays.toString(getCollectionNames()));
    sb.append(", mongoConnectorName='").append(mongoConnectorName).append('\'');
    sb.append(", mongoDatabaseName='").append(mongoDatabaseName).append('\'');
    sb.append(", password='").append(password).append('\'');
    sb.append(", questionCollection='").append(questionCollection).append('\'');
    sb.append(", questionTable='").append(questionTable).append('\'');
    sb.append(", schoolCollection='").append(schoolCollection).append('\'');
    sb.append(", schoolTable='").append(schoolTable).append('\'');
    sb.append(", sqlConnectorName='").append(sqlConnectorName).append('\'');
    sb.append(", sqlDatabaseName='").append(sqlDatabaseName).append('\'');
    sb.append(", tableNames=").append(Arrays.toString(getTableNames()));
    sb.append(", teamCollection='").append(teamCollection).append('\'');
    sb.append(", teamTable='").append(teamTable).append('\'');
    sb.append(", userName='").append(userName).append('\'');
    sb.append('}');
    return sb.toString();
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

  public String[] getTableNames() {
    return new String[]{schoolTable, teamTable, questionTable};
  }

  public String[] getCollectionNames() {
    return new String[]{schoolCollection, teamCollection, questionCollection};
  }
}
