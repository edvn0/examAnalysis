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

public class DatabaseLoginUser {

  private String userName;
  private String password;
  private String databaseName, connector;
  private String sColl, tColl, qColl; // tables/collections.

  DatabaseLoginUser(String userName, char[] password, String connector, String databaseName,
      String sColl, String tColl, String qColl) {
    boolean validated = validateUser(userName, password);
    this.password = validated ? String.valueOf(password) : null;
    this.userName = validated ? userName : null;
    this.databaseName = databaseName;
    this.connector = connector;
    this.sColl = sColl;
    this.tColl = tColl;
    this.qColl = qColl;
  }

  public DatabaseLoginUser(String userName, char[] password, String inputDatabase) {
    this.userName = userName;
    this.password = String.copyValueOf(password);
    this.databaseName = inputDatabase;
  }

  public boolean validateUser(String user, char[] password) {
    return user.length() > 0 && user.length() < 16 && password.length > 0 && password.length < 16;
  }

  @Override
  public String toString() {
    return "DatabaseLoginUser{" + "password='" + password + '\''
        + ", userName='" + userName + '\''
        + '}';
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public String getSchoolColl() {
    return sColl;
  }

  public String getTeamColl() {
    return tColl;
  }

  public String getQuestionColl() {
    return qColl;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public String getConnector() {
    return connector;
  }
}
