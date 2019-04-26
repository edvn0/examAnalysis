/*
 * Copyright (c) 2018-2019 Edwin Carlsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.bth.exammain;

import com.bth.analysis.ExamAnalysis;
import com.bth.analysis.stats.StatsSchool;
import com.bth.analysis.stats.StatsTeam;
import com.bth.analysis.stats.helperobjects.RoundOffStatsQuestion;
import com.bth.gui.controller.DatabaseOutputController;
import com.bth.gui.controller.loginusers.MongoDBUser;
import com.bth.gui.controller.loginusers.SQLLoginUser;
import com.bth.io.input.CliReader;
import com.bth.io.input.FileInput;
import com.bth.io.input.PropertiesReader;
import com.bth.io.output.ExamOutput;
import com.bth.io.output.database.mongodb.mongodbconnector.MongoDBConnection;
import com.bth.io.output.database.sql.sqlconnector.MySqlConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

class ExamProjectHandler {

  private DatabaseOutputController controller;
  private ExamAnalysis examAnalysis;
  private CliReader reader;
  private ExamOutput output;

  private PropertiesReader propertiesReader;

  private boolean started;
  private boolean connected;

  ExamProjectHandler() {
    reader = new CliReader();
    examAnalysis = new ExamAnalysis();
    controller = new DatabaseOutputController();
    output = new ExamOutput();
    if (ExamMain.dev) {
      try {
        propertiesReader = new PropertiesReader(
            "/Users/edwincarlsson/Documents/Programmering/exam_Analysis/.properties");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  void main() {
    Scanner scanner = new Scanner(System.in);
    System.out.println(
        "Welcome to the Exam analyser. Choose from the following list of actions. Start with 1."
            + "\n1: Get all data from your database of exam answers."
            + "\n2: Connect to your own databases to output data."
            + "\n3: Output data into CSV files."
            + "\n4: Insert into databases connected to in 2)."
            + "\n5: Disconnect from database."
            + "\n6: Exit.");
    while (scanner.hasNext()) {
      String input = scanner.nextLine();
      switch (input) {
        case "1":
          // Input from the database, where all the questions are.
          if (ExamMain.dev) {
            this.dev_input();
          } else {
            initializeExamAnalysis();
          }
          main();
          break;
        case "2":
          if (started) {
            if (ExamMain.dev) {
              this.dev_connect();
            } else {
              String in = scanner.nextLine().trim();
              // Connect to both databases.
              initializeDatabaseConnections(in);
            }
          } else {
            System.out.println("You need to have the input database connected, try 1 again.");
          }
          main();
          break;
        case "3":
          if (started) {
            // Output to three (4 dev) csv files.
            initializeExamOutput();
          } else {
            System.out.println("You need to have the input database connected, try 1 again.");
          }
          main();
          break;
        case "4":
          if (started && connected) {
            initializeDatabaseInsertions();
          } else {
            System.out.println("You were not connected, try again by menu option 1.");
          }
          main();
          break;
        case "5":
          System.out.println("Input which database you want to connect, or both by:"
              + "\n'mongodb','mdb','mongo': Mongo DB connection."
              + "\n'mysql', 'sql': MySQL database connection."
              + "\n'both': Both databases.");
          String select = scanner.next().trim();
          if (connected) {
            disconnectFromDatabases(select);
          } else {
            System.out.println("The databases are already not connected.");
          }
          break;
        case "6":
          System.exit(0);
        default:
          main();
      }
    }
  }

  private void dev_connect() {
    String[] inputsMySql = {
        propertiesReader.getProperty("connect.string.for.mysql"),
        propertiesReader.getProperty("mysql.databaseName"),
        propertiesReader.getProperty("mysql.schoolTable"),
        propertiesReader.getProperty("mysql.teamTable"),
        propertiesReader.getProperty("mysql.questionTable"),
        propertiesReader.getProperty("mysql.username"),
        propertiesReader.getProperty("mysql.password"),
    };

    String[] inputsMongoDb = {
        propertiesReader.getProperty("connect.string.for.mongo"),
        propertiesReader.getProperty("mongo.databaseName"),
        propertiesReader.getProperty("mongo.schoolColl"),
        propertiesReader.getProperty("mongo.teamColl"),
        propertiesReader.getProperty("mongo.questionColl"),
        propertiesReader.getProperty("mongo.username"),
        propertiesReader.getProperty("mongo.password"),
    };

    SQLLoginUser sUser = new SQLLoginUser(
        inputsMySql[5],
        inputsMySql[6].toCharArray(),
        inputsMySql[1],
        inputsMySql[2],
        inputsMySql[3],
        inputsMySql[4],
        inputsMySql[0]);
    MySqlConnection sqlConnection = new MySqlConnection(sUser);
    controller.setConnection(sqlConnection);
    controller.getSqlConnection().connect(sUser);

    MongoDBUser mUser = new MongoDBUser(
        inputsMongoDb[5],
        inputsMongoDb[6].toCharArray(),
        inputsMongoDb[1],
        inputsMongoDb[2],
        inputsMongoDb[3],
        inputsMongoDb[4],
        inputsMongoDb[0]);
    MongoDBConnection mongoDBConnection = new MongoDBConnection(mUser);
    controller.setConnection(mongoDBConnection);
    controller.getMongoDBConnection().connect(mUser);

    connected = true;
  }

  private void dev_input() {
    if (propertiesReader != null) {
      SQLLoginUser user = new SQLLoginUser(
          propertiesReader.getProperty("mysql.username"),
          propertiesReader.getProperty("mysql.password").toCharArray(),
          propertiesReader.getProperty("mysql.inputTable"),
          propertiesReader.getProperty("connect.string.for.mysql"));

      examAnalysis.setUser(user);

      examAnalysis.startWithDatabase();
      started = true;

    }
  }

  private void disconnectFromDatabases(String select) {
    switch (select) {
      case "mongodb":
      case "mongo":
      case "mdb":
        controller.getSqlConnection().disconnect();
        break;
      case "sql":
      case "mysql":
        controller.getMongoDBConnection().disconnect();
        break;
      case "both":
        controller.getMongoDBConnection().disconnect();
        controller.getSqlConnection().disconnect();
        break;
      default:
        System.out.println("Incorrect input, try again.");
        break;
    }
  }

  private void initializeDatabaseInsertions() {
    List<StatsSchool> statsSchools = examAnalysis.getStatsSchools();
    List<StatsTeam> statsTeams = examAnalysis.getStatsTeams();
    List<RoundOffStatsQuestion> questions = examAnalysis.getQuestionsStats();

    String sTable = controller.getSqlConnection().getUser().getSchoolTable();
    String tTable = controller.getSqlConnection().getUser().getTeamTable();
    String qTable = controller.getSqlConnection().getUser().getQuestionTable();

    System.out.println(sTable + " " + tTable + " " + qTable);

    String sColl = controller.getMongoDBConnection().getUser().getSchoolColl();
    String tColl = controller.getMongoDBConnection().getUser().getTeamColl();
    String qColl = controller.getMongoDBConnection().getUser().getQuestionColl();

    System.out.println(sColl + " " + tColl + " " + qColl);

    controller.insertIntoMongoDatabase(null, null, questions, sColl, tColl, qColl,
        examAnalysis);
    controller.insertIntoMongoDatabase(statsSchools, null, null, sColl, tColl, qColl,
        examAnalysis);
    controller.insertIntoMongoDatabase(null, statsTeams, null, sColl, tColl, qColl,
        examAnalysis);
    try {
      controller
          .insertIntoMySQLDatabase(null, null, questions, sTable, tTable, qTable,
              examAnalysis);
      controller
          .insertIntoMySQLDatabase(null, statsTeams, null, sTable, tTable, qTable,
              examAnalysis);
      controller
          .insertIntoMySQLDatabase(statsSchools, null, null, sTable, tTable, qTable,
              examAnalysis);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void initializeDatabaseConnections(String in) {
    List<String> s1;
    List<String> s2;

    boolean mdb = false;
    boolean mysql = false;
    if (in.equals("mongodb") || in.equals("mongo") || in.equals("mdb")) {
      mdb = true;
    } else if (in.equals("sql") || in.equals("mysql")) {
      mysql = true;
    } else {
      System.out.println("Incorrect input, try again.");
    }

    /* Input from the lists.
    0 = connector.
    1 = name of database.
    2 = name of school output table/collection.
    3 = name of team output table/collection.
    4 = name of question output table/collection.
    5 = username.
    6 = password.
    */

    // SQL, true => sql
    // boolean whichDatabase = false => sql.
    if (mysql) {
      s1 = reader.readInputInformationDatabase(true);
      SQLLoginUser sUser = new SQLLoginUser(
          s1.get(5),
          s1.get(6).toCharArray(),
          s1.get(1),
          s1.get(2),
          s1.get(3),
          s1.get(4),
          s1.get(0));

      // Validate databases or return.
      MySqlConnection sqlConnection = new MySqlConnection(sUser);

      if (!FileInput.validateConnectorString(sUser.getConnector())) {
        System.out.println("SQL database was not connected. Check your input and try again.");
        return;
      }

      controller.setConnection(sqlConnection);
      controller.getSqlConnection().connect(sUser);
    }

    // MongoDB, false => mongodb.
    // boolean whichDatabase = true => mongodb.
    if (mdb) {
      s2 = reader.readInputInformationDatabase(false);
      MongoDBUser mUser = new MongoDBUser(
          s2.get(5),
          s2.get(6).toCharArray(),
          s2.get(1),
          s2.get(2),
          s2.get(3),
          s2.get(4),
          s2.get(0));

      // Validate databases or return.
      MongoDBConnection mongoDBConnection = new MongoDBConnection(mUser);
      if (!FileInput.validateConnectorString(mUser.getConnector())) {
        System.out.println("MongoDB database was not connected. Check your input and try again.");
        return;
      }

      controller.setConnection(mongoDBConnection);
      controller.getMongoDBConnection().connect(mUser);
    }

    if (mdb || mysql) {
      connected = true;
      return;
    }

  }

  private void initializeExamOutput() {
    List<String> strings = reader.readInputAboutOutput();
    output = new ExamOutput();
    output.setDirectory(strings.get(0));

    output.printToCSV_Questions(examAnalysis.getQuestionsStats(), examAnalysis);
    output.printToCSV_Schools(examAnalysis.getStatsSchools(), examAnalysis);
    output.printToCSV_Teams(examAnalysis.getStatsTeams());
  }

  private void initializeExamAnalysis() {
    List<String> stringList;
    stringList = reader.readInputInformationFromCli();

    String connector = stringList.get(0);
    String userName = stringList.get(1);
    char[] pass = stringList.get(2)
        .toCharArray();
    String table = stringList.get(3);

    SQLLoginUser user = new SQLLoginUser(userName, pass, table, connector);

    // validate connector.
    System.out.println("Validating connector.");
    boolean validatedConnector = FileInput
        .validateConnectorString(connector);

    if (!validatedConnector) {
      System.out.println("Could not validate database connector.");
      return;
    }
    System.out.println("Validated connector.");
    // end connector validation.

    examAnalysis
        .setUser(user);

    examAnalysis.startWithDatabase();

    started = true;

  }
}
