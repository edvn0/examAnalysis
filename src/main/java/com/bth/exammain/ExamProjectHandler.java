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
import com.bth.io.output.ExamOutput;
import com.bth.io.output.database.mongodb.mongodbconnector.MongoDBConnection;
import com.bth.io.output.database.sql.sqlconnector.MySqlConnection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

class ExamProjectHandler {

  private DatabaseOutputController controller;
  private ExamAnalysis examAnalysis;
  private CliReader reader;
  private ExamOutput output;

  private boolean started;
  private boolean connected;

  ExamProjectHandler() {
    reader = new CliReader();
    examAnalysis = new ExamAnalysis();
    controller = new DatabaseOutputController();
    output = new ExamOutput();
  }

  void main() {
    Scanner scanner = new Scanner(System.in);
    System.out.println(
        "Welcome to the Exam analyser. Choose from the following list of actions. Start with 1."
            + "\n1: Get all data from your database of exam answers."
            + "\n2: Connect to your own databases to output data."
            + "\n3: Output data into CSV files."
            + "\n4: Insert into databases connected to in 2)."
            + "\n5: Exit.");
    while (scanner.hasNext()) {
      String input = scanner.nextLine();
      switch (input) {
        case "1":
          // Input from the database, where all the questions are.
          initializeExamAnalysis();
          main();
          break;
        case "2":
          if (started) {
            // Connect to both databases.
            initializeDatabaseConnections();
          }
          main();
          break;
        case "3":
          if (started) {
            // Output to three (4 dev) csv files.
            initializeExamOutput();
          }
          main();
          break;
        case "4":
          if (started && connected) {
            initializeDatabaseInsertions();
          }
          main();
          break;
        case "5":
          System.exit(0);
          break;
        default:
          main();
      }
    }
  }

  private void initializeDatabaseInsertions() {
    List<StatsSchool> statsSchools = examAnalysis.getStatsSchools();
    List<StatsTeam> statsTeams = examAnalysis.getStatsTeams();
    List<RoundOffStatsQuestion> questions = examAnalysis.getQuestionsStats();

    String sTable = controller.getSqlConnection().getUser().getSchoolTable();
    String tTable = controller.getSqlConnection().getUser().getTeamTable();
    String qTable = controller.getSqlConnection().getUser().getQuestionTable();

    String sColl = controller.getMongoDBConnection().getUser().getSchoolColl();
    String tColl = controller.getMongoDBConnection().getUser().getTeamColl();
    String qColl = controller.getMongoDBConnection().getUser().getQuestionColl();

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

  private void initializeDatabaseConnections() {
    List<String> s1;
    List<String> s2;

    // SQL, true => sql
    s1 = reader.readInputInformationDatabase(true);
    SQLLoginUser sUser = new SQLLoginUser(s1.get(5), s1.get(6).toCharArray(), s1.get(1), s1.get(2),
        s1.get(3), s1.get(4), s1.get(0));

    // MongoDB, false => mongodb.
    s2 = reader.readInputInformationDatabase(false);
    MongoDBUser mUser = new MongoDBUser(s2.get(5), s2.get(6).toCharArray(), s2.get(1), s2.get(2),
        s2.get(3), s2.get(4), s2.get(0));

    MongoDBConnection mongoDBConnection = new MongoDBConnection(mUser);
    MySqlConnection sqlConnection = new MySqlConnection(sUser);

    controller.setConnection(sqlConnection);
    controller.setConnection(mongoDBConnection);

    connected = true;

    JOptionPane.showMessageDialog(null, "You were connected!");
  }

  private void initializeExamOutput() {
    List<String> strings = reader.readInputAboutOutput();
    output = new ExamOutput();
    output.setDirectory(strings.get(0));

    output.printToCSV_Questions(examAnalysis.getQuestionsStats(), examAnalysis);
    output.printToCSV_Schools(examAnalysis.getStatsSchools(), examAnalysis);
    output.printToCSV_Teams(examAnalysis.getStatsTeams());

    JOptionPane
        .showMessageDialog(null, ""
            + "Successfully printed to csv files in " + strings.get(0) + "!");

  }

  private void initializeExamAnalysis() {
    List<String> stringList;
    stringList = reader.readInputInformationFromCli();

    String userName = stringList.get(1);
    String connector = stringList.get(0);
    String table = stringList.get(3);
    char[] pass = stringList.get(2).toCharArray();

    examAnalysis
        .setUser(new SQLLoginUser(userName, pass, table, connector));

    JOptionPane.showMessageDialog(null, "Exam analysis was successfully initiated!");

    examAnalysis.startWithDatabase();

    started = true;

  }
}
