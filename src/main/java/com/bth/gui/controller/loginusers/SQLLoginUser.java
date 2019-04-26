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

  private String connector;
  private String databaseName = null;

  public SQLLoginUser(String userName, char[] password, String databaseName,
      String schoolTable, String teamTable,
      String questionTable, String connector) {
    super(userName, password, connector, databaseName, schoolTable, teamTable, questionTable);
    this.connector = connector;
  }

  public SQLLoginUser(String userName, char[] password, String inputTable, String connector) {
    super(userName, password, inputTable);
    this.connector = connector;
  }

  public String getTeamTable() {
    return super.getTeamColl();
  }

  public String getSchoolTable() {
    return super.getSchoolColl();
  }

  public String getQuestionTable() {
    return super.getQuestionColl();
  }

  public String getConnector() {
    return connector;
  }

  public String getDatabaseName() {
    return super.getDatabaseName();
  }

  public void setConnector(String db) {
    this.connector = db;
  }

  public void setDatabaseName(String property) {
    this.databaseName = property;
  }
}
