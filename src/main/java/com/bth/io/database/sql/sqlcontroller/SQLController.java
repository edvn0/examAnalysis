package com.bth.io.database.sql.sqlcontroller;

import com.bth.analysis.ExamAnalysis;
import com.bth.analysis.Stats.StatsSchool;
import com.bth.analysis.Stats.StatsTeam;
import com.bth.analysis.Stats.helperobjects.RoundOffStatsQuestion;
import com.bth.gui.controller.DatabaseLoginUser;
import com.bth.io.ExamInput;
import com.bth.io.database.sql.sqlconnector.SQLConnector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SQLController {
  public static void setDatabaseLoginUser(DatabaseLoginUser databaseLoginUser) {
    SQLConnector.databaseLoginUser = databaseLoginUser;
  }

  /***
   *
   * @param con Connection from SQL
   * @param teamList List of teams
   * @param schoolList List of schools
   * @param rosqList List of questions
   * @throws SQLException Because SQL sucks
   */
  public static void insertIntoDatabase(Connection con, List<StatsTeam> teamList,
                                        List<StatsSchool> schoolList,
                                        List<RoundOffStatsQuestion> rosqList) throws SQLException {
    if (teamList == null && schoolList != null && rosqList == null) {
      for (StatsSchool school : schoolList) {
        String sql = "insert into stats_exams.school_statistics (name,score,mean,standarddev,variance,median) values (?,?,?,?,?,?)";
        PreparedStatement statement = con.prepareStatement(sql);
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
        String sql = "insert into stats_exams.team_statistics (name,score,mean,standarddev,variance,median) values (?,?,?,?,?,?)";
        PreparedStatement statement = con.prepareStatement(sql);
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
      boolean doesQuestionsExist = checkDatabaseForQuestions(con, rosqList);

      if (!doesQuestionsExist) {
        for (RoundOffStatsQuestion question : rosqList) {
          String q = Integer.toString(Integer.parseInt(question.getQuestion()) + 1);
          String sQ = "q".concat(q);
          String sql = "insert into " +
              "stats_exams.questions " +
              "(question, date, mean, median, stddev, variance, max_score) " +
              "values (?,?,?,?,?,?,?)";

          int maxScore = ExamInput.getMaxScore(q);
          if (maxScore != -1) {
            PreparedStatement statement = con.prepareStatement(sql);
            setStatementWithPS(statement, 0,
                question.getMean(), question.getStddev(),
                question.getVariance(), question.getMedian(), null, null, sQ, maxScore);
          } else {
            throw new SQLException("Max Score was not found.");
          }
        }
      }
    } else {
      con.close();
      System.err.println("Error! Input either schoolexams or teamexams.");
      System.exit(-1);
    }
  }

  private static boolean checkDatabaseForQuestions(Connection con, List<RoundOffStatsQuestion> rosqList) {
    int equalQuestions = 0;
    try {
      ResultSet rs = con.prepareStatement("SELECT * FROM stats_exams.questions").executeQuery();
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
        if (question.equals(sQ) && mean >= 0 && median >= 0 && stddev >= 0 && variance >= 0 && maxscore >= 0) {
          equalQuestions++;
        }
      }
      return equalQuestions == ExamAnalysis.AMOUNT_OF_QUESTIONS;
    } catch (SQLException e) {
      System.out.println(e.getSQLState());
    }
    return false;
  }

  private static void setStatementWithPS(PreparedStatement statement, double score,
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

  private static void writeStringToStatement(PreparedStatement statement, double score,
                                             double mean, double stddev, double variance,
                                             double median) throws SQLException {
    statement.setString(2, "" + score);
    statement.setString(3, "" + mean);
    statement.setString(4, "" + stddev);
    statement.setString(5, "" + variance);
    statement.setString(6, "" + median);
  }

}
