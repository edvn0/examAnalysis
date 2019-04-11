/*
 * Copyright (c) 2018-2019 Edwin Carlsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.bth.gui.controller.loginusers;

public class SQLLoginUser extends DatabaseLoginUser {

  private String teamTable, schoolTable, questionTable, connector, databaseName;

  public SQLLoginUser(String userName, char[] password, String databaseName,
      String schoolTable, String teamTable,
      String questionTable, String connector) {
    super(userName, password);

    try {
      this.connector = validateDatabase(connector);
    } catch (Exception e) {
      e.printStackTrace();
    }

    this.databaseName = databaseName;
    this.teamTable = teamTable;
    this.schoolTable = schoolTable;
    this.questionTable = questionTable;
  }

  public SQLLoginUser(String userName, char[] password, String inputTable, String connector) {
    super(userName, password);
    this.connector = connector;
    this.databaseName = inputTable;
  }

  public String validateDatabase(String db) throws Exception {
    System.out.println(db);
    boolean startsWithMySql = db.startsWith("jdbc:mysql://");
    System.out.println(db.startsWith("jdbc:mysql://"));
    if (!startsWithMySql) {
      String exception = "Database is not validated.";
      throw new Exception(exception);
    }
    return db;
  }

  public String getTeamTable() {
    return teamTable;
  }

  public String getSchoolTable() {
    return schoolTable;
  }

  public String getQuestionTable() {
    return questionTable;
  }

  public String getConnector() {
    return connector;
  }

  public String getDatabaseName() {
    return databaseName;
  }
}
