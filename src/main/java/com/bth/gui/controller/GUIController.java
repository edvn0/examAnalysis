package com.bth.gui.controller;

import com.bth.analysis.Stats.StatsSchool;
import com.bth.analysis.Stats.StatsTeam;
import com.bth.analysis.Stats.helperobjects.RoundOffStatsQuestion;
import com.bth.gui.MainGUI;
import com.bth.gui.csvchooser.CsvDirectoryChoice;
import com.bth.gui.login.LoginDatabase;
import com.bth.io.ExamInput;
import com.bth.io.database.mongodb.mongodbconnector.ExamMongoDBObject;
import com.bth.io.database.mongodb.mongodbconnector.MongoDBConnection;
import com.bth.io.database.sql.sqlconnector.MySqlConnection;
import com.mongodb.BasicDBObject;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

public class GUIController {

  public static boolean dbChoice = false; // SQL if TRUE.

  private static MongoDBConnection connection;
  private static MySqlConnection mySqlConnection;

  private CsvDirectoryChoice csvDirectoryChoice;
  private LoginDatabase database;
  private MainGUI mainGUI;

  public GUIController() {
  }

  public static void setConnection(MySqlConnection conn) {
    mySqlConnection = conn;
  }

  public MongoDBConnection getMDBConnection() {
    return this.database.getMongoDBConnection();
  }

  public MySqlConnection getMySqlConnection() {
    return this.database.getMySqlConnection();
  }

  public void setEnabledForAll(ArrayList<JComponent> component, boolean value) {
    for (JComponent c : component) {
      c.setEnabled(value);
    }
  }

  public static void setConnection(MongoDBConnection conn) {
    connection = conn;
  }

  public void append(LoginDatabase database) {
    this.database = database;
  }

  public void append(CsvDirectoryChoice csvDirectoryChoice) {
    this.csvDirectoryChoice = csvDirectoryChoice;
  }

  public void append(MainGUI gui) {
    this.mainGUI = gui;
  }

  public LoginDatabase getLoginDatabase() {
    return database;
  }

  public CsvDirectoryChoice getCsvDirectoryChoice() {
    return csvDirectoryChoice;
  }

  public MainGUI getMainGUI() {
    return mainGUI;
  }

  public void insertIntoMongoDatabase(List<StatsSchool> schoolList,
      List<StatsTeam> teamList,
      List<RoundOffStatsQuestion> questionList,
      String sColl, String tColl, String qColl) {
    boolean schoolListNull = schoolList == null;
    boolean teamListNull = teamList == null;
    boolean questionListNull = questionList == null;
    boolean schoolTeamListNull =
        schoolListNull && teamListNull && !questionListNull; // => we will insert questions
    boolean schoolQuestionListNull =
        schoolListNull && questionListNull && !teamListNull; // => we will insert teams
    boolean teamQuestionNull =
        teamListNull && questionListNull && !schoolListNull; // => we will insert schools

    List<BasicDBObject> objects = new ArrayList<>();

    String use = null;

    if (teamQuestionNull) {
      for (StatsSchool school : schoolList) {
        objects.add(ExamMongoDBObject.toDBObject(school));
      }
      use = sColl;
    } else if (schoolQuestionListNull) {
      for (StatsTeam team : teamList) {
        objects.add(ExamMongoDBObject.toDBObject(team));
      }
      use = tColl;
    } else if (schoolTeamListNull) {
      for (RoundOffStatsQuestion question : questionList) {
        objects.add(ExamMongoDBObject.toDBObject(question));
      }
      use = qColl;
    }
    connection.getMongoDatabase().getCollection(use, BasicDBObject.class).insertMany(objects);
  }

  /***
   *
   * @param teamList List of teams
   * @param schoolList List of schools
   * @param rosqList List of questions
   * @throws SQLException if NoInputFound
   */
  @SuppressWarnings("all")
  public void insertIntoMySQLDatabase(List<StatsTeam> teamList,
      List<StatsSchool> schoolList,
      List<RoundOffStatsQuestion> rosqList, String table) throws SQLException {
    if (teamList == null && schoolList != null && rosqList == null) {
      for (StatsSchool school : schoolList) {
        String sql = "insert into " + mySqlConnection.getUser().getSqlDatabaseName() + "." + table +
            " (name,score,mean,standarddev,variance,median) values (?,?,?,?,?,?)";
        PreparedStatement statement = mySqlConnection.getConnection().prepareStatement(sql);
        setStatementWithPS(statement,
            school.getScore(),
            school.getMean(),
            school.getStddev(),
            school.getVariance(),
            school.getMedian(),
            null,
            school,
            null,
            0);
      }
    } else if (schoolList == null && teamList != null && rosqList == null) {

      for (StatsTeam team : teamList) {
        String sql = "insert into " + mySqlConnection.getUser().getSqlDatabaseName() + "." + table +
            " (name,score,mean,standarddev,variance,median) values (?,?,?,?,?,?)";
        PreparedStatement statement = mySqlConnection.getConnection().prepareStatement(sql);
        setStatementWithPS(statement,
            team.getScore(),
            team.getMean(),
            team.getStddev(),
            team.getVariance(),
            team.getMedian(),
            team,
            null,
            null,
            0);
      }
    } else if (schoolList == null && teamList == null && rosqList != null) {

      // Inserts the questions 1-14 into stats_exams.questions if the questions already do not exist.
      boolean doesQuestionsExist = checkDatabaseForQuestions(rosqList);

      if (!doesQuestionsExist) {
        for (RoundOffStatsQuestion question : rosqList) {
          String q = Integer.toString(Integer.parseInt(question.getQuestion()) + 1);
          String sQ = "q".concat(q);
          String sql = "insert into " +
              mySqlConnection.getUser().getSqlDatabaseName() + "." + table +
              " (question, date, mean, median, stddev, variance, max_score) " +
              "values (?,?,?,?,?,?,?)";

          int maxScore = ExamInput.getMaxScore(q);
          if (maxScore != -1) {
            PreparedStatement statement = mySqlConnection.getConnection().prepareStatement(sql);
            setStatementWithPS(statement, 0,
                question.getMean(), question.getStddev(),
                question.getVariance(), question.getMedian(), null, null, sQ, maxScore);
          } else {
            throw new SQLException("Max Score was not found.");
          }
        }
      }
    } else {
      mySqlConnection.getConnection().close();
      System.err.println("Error! Input either schoolexams or teamexams.");
      System.exit(-1);
    }
  }

  private void setStatementWithPS(PreparedStatement statement, double score,
      double mean, double stddev, double variance,
      double median, StatsTeam team, StatsSchool school,
      String question, int maxscore) throws SQLException {
    if (question != null && school == null && team == null) {
      BigDecimal bdMean = new BigDecimal(mean);
      BigDecimal bdMedian = new BigDecimal(median);
      BigDecimal bdStddev = new BigDecimal(stddev);
      BigDecimal bdVariance = new BigDecimal(variance);
      statement.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
      statement.setString(1, question);
      statement.setBigDecimal(3, bdMean);
      statement.setBigDecimal(4, bdMedian);
      statement.setBigDecimal(5, bdStddev);
      statement.setBigDecimal(6, bdVariance);
      statement.setInt(7, maxscore);
    } else if (team == null) {
      statement.setString(1, school.getSchool());
      writeStringToStatement(statement, score, mean, stddev, variance, median);
    } else if (school == null) {
      statement.setString(1, team.getTeam());
      writeStringToStatement(statement, score, mean, stddev, variance, median);
    }
    statement.execute();
  }

  @SuppressWarnings("all")
  private boolean checkDatabaseForQuestions(List<RoundOffStatsQuestion> rosqList) {
    int equalQuestions = 0;
    try {
      ResultSet rs = mySqlConnection.getConnection()
          .prepareStatement("SELECT * FROM stats_exams.questions").executeQuery();
      int k = 0;
      while (rs.next()) {
        String question = rs.getString(2);
        double mean = Double.parseDouble(rs.getString(4));
        double median = Double.parseDouble(rs.getString(5));
        double stddev = Double.parseDouble(rs.getString(6));
        double variance = Double.parseDouble(rs.getString(7));
        double maxscore = Double.parseDouble(rs.getString(8));
        String q = Integer.toString(Integer.parseInt(rosqList.get(k++).getQuestion()) + 1);
        String sQ = "q".concat(q);
        if (question.equals(sQ) && mean >= 0 && median >= 0 && stddev >= 0 && variance >= 0
            && maxscore >= 0) {
          equalQuestions++;
        }
      }
      // TODO: fix hard coded 14 here, might change in future.
      return equalQuestions == 14;
    } catch (SQLException e) {
      System.out.println(e.getSQLState());
    }
    return false;
  }

  private void writeStringToStatement(PreparedStatement statement, double score,
      double mean, double stddev, double variance,
      double median) throws SQLException {
    statement.setString(2, "" + score);
    statement.setString(3, "" + mean);
    statement.setString(4, "" + stddev);
    statement.setString(5, "" + variance);
    statement.setString(6, "" + median);
  }
}
