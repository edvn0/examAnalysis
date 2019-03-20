package com.bth.io;

import com.bth.analysis.ExamAnalysis;
import com.bth.analysis.Stats.StatsSchool;
import com.bth.analysis.Stats.StatsTeam;
import com.bth.analysis.Stats.helperobjects.RoundOffStatsQuestion;
import com.bth.exams.ExamSchool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Comparator;
import java.util.List;

public class ExamOutput
{
  private static String directory = "/Users/edwincarlsson/Documents/Programmering/Java-programmering/src/main/java/com.bth/data/output";

  public static void printToCSV_Teams(List<StatsTeam> teamList)
  {
    teamList.sort(Comparator.comparing(StatsTeam::getScore));

    try (PrintWriter writer = new PrintWriter(new File(directory + "/output_teams.csv")))
    {
      writeHeaderWithPrintWriter(writer, false);

      for (StatsTeam team : teamList)
      {
        StringBuilder stringBuilder = new StringBuilder();
        getStringRepresentation(writer,
            stringBuilder,
            team.getScore(),
            team.getStddev(),
            team.getMean(),
            team.getMedian(),
            team.getVariance(),
            null,
            team);
      }
    } catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  public static void printToCSV_Questions(List<RoundOffStatsQuestion> questions)
  {
    try (PrintWriter writer = new PrintWriter(new File(directory + "/output_questions.csv")))
    {
      writeHeaderWithPrintWriter(writer, true);

      for (RoundOffStatsQuestion rosQ : questions)
      {
        StringBuilder builder = new StringBuilder();
        String q = Integer.toString(Integer.parseInt(rosQ.getQuestion()) + 1);
        int index = Integer.parseInt(rosQ.getQuestion()) + 1;
        String question = String.valueOf(index);
        builder.append(question).append(",");
        builder.append(rosQ.getMean()).append(",");
        builder.append(rosQ.getMedian()).append(",");
        builder.append(rosQ.getStddev()).append(",");
        builder.append(rosQ.getVariance()).append(",");
        builder.append(ExamInput.getMaxScore(q));
        builder.append("\n");
        writer.write(builder.toString());
      }
    } catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  public static void printToCSV_Schools(List<StatsSchool> statsSchools)
  {
    statsSchools.sort(Comparator.comparing(StatsSchool::getScore));

    try (PrintWriter writer = new PrintWriter(new File(directory + "/output_schools.csv")))
    {
      writeHeaderWithPrintWriter(writer, false);

      for (StatsSchool statsSchool : statsSchools)
      {
        StringBuilder stringBuilder = new StringBuilder();
        getStringRepresentation(writer, stringBuilder, statsSchool.getScore(), statsSchool.getStddev(), statsSchool.getMean(), statsSchool.getMedian(), statsSchool.getVariance(), statsSchool, null);
      }
    } catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  public static void printQuestionsToCSV(ExamSchool[] statsSchools)
  {
    // Headers:
    /*
     * school:
     * questions = [1,2,3,4,5,6,7,8,9,10,11,12,13,14]
     */
    try (PrintWriter writer = new PrintWriter(directory + "/output_onetofourteenandschools.csv"))
    {
      writer.write("School,");
      for (int i = 1; i <= 14; i++)
      {
        if (i < 14)
          writer.write("q" + i + ",");
        else
          writer.write("q" + i);
      }
      writer.write("\n");

      for (ExamSchool schools : statsSchools)
      {
        double[] scores = schools.getSeparateScoresForAllQuestions();
        writer.write(schools.getSchool() + ",");
        for (int i = 0; i < scores.length; i++)
        {
          writer.write("" + scores[i]);
          if (i != 13) writer.write(",");
        }
        writer.write("\n");
      }

    } catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }


  }

  private static void writeHeaderWithPrintWriter(PrintWriter writer, boolean questions)
  {
    if (!questions)
    {
      writer.write("Name,");
      writer.write("Score,");
    } else
    {
      writer.write("Question,");
    }
    writer.write("Mean,");
    writer.write("Median,");
    writer.write("Standard Deviation,");
    writer.write("Variance");
    if (questions)
    {
      writer.write(",");
      writer.write("Max Score");
    }
    writer.write("\n");
  }

  private static void getStringRepresentation(PrintWriter writer, StringBuilder stringBuilder, double score2, double stddev2, double mean2, double median2, double variance2, StatsSchool statsSchool, StatsTeam statsTeam)
  {
    String name = "";
    if (statsSchool == null)
    {
      name += statsTeam.getTeam();
    } else if (statsTeam == null)
    {
      name += statsSchool.getSchool();
    }
    String score = "," + score2;
    String stddev = "," + stddev2;
    String mean = "," + mean2;
    String median = "," + median2;
    String variance = "," + variance2;

    stringBuilder.append(name);
    stringBuilder.append(score);
    stringBuilder.append(mean);
    stringBuilder.append(median);
    stringBuilder.append(stddev);
    stringBuilder.append(variance);
    stringBuilder.append('\n');

    writer.write(stringBuilder.toString());
  }

  private static void setStatementWithPS(PreparedStatement statement, double score, double mean, double stddev, double variance, double median, StatsTeam team, StatsSchool school, String question, int maxscore) throws SQLException
  {
    if (question != null && school == null && team == null)
    {
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
    } else if (team == null)
    {
      statement.setString(1, school.getSchool());
      writeStringToStatement(statement, score, mean, stddev, variance, median);
    } else if (school == null)
    {
      statement.setString(1, team.getTeam());
      writeStringToStatement(statement, score, mean, stddev, variance, median);
    }
    statement.execute();
  }

  private static void writeStringToStatement(PreparedStatement statement, double score, double mean, double stddev, double variance, double median) throws SQLException
  {
    statement.setString(2, "" + score);
    statement.setString(3, "" + mean);
    statement.setString(4, "" + stddev);
    statement.setString(5, "" + variance);
    statement.setString(6, "" + median);
  }

  public static void insertIntoDatabase(List<StatsTeam> teamList, List<StatsSchool> schoolList, List<RoundOffStatsQuestion> rosqList)
  {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:8889/stats_exams",
        "Edwin",
        "Edwin98"))
    {

      if (teamList == null && schoolList != null && rosqList == null)
      {
        for (StatsSchool school : schoolList)
        {
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
        con.close();
      } else if (schoolList == null && teamList != null && rosqList == null)
      {
        for (StatsTeam team : teamList)
        {
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
        con.close();
      } else if (schoolList == null && teamList == null && rosqList != null)
      {
        // Inserts the questions 1-14 into stats_exams.questions if the questions already do not exist.
        boolean doesQuestionsExist = checkDatabaseForQuestions(con, rosqList);

        if (!doesQuestionsExist)
        {
          for (RoundOffStatsQuestion question : rosqList)
          {
            String q = Integer.toString(Integer.parseInt(question.getQuestion()) + 1);
            String sQ = "q".concat(q);
            String sql = "insert into " +
                "stats_exams.questions " +
                "(question, date, mean, median, stddev, variance, max_score) " +
                "values (?,?,?,?,?,?,?)";

            int maxScore = ExamInput.getMaxScore(q);
            if (maxScore != -1)
            {
              PreparedStatement statement = con.prepareStatement(sql);
              setStatementWithPS(statement, 0,
                  question.getMean(), question.getStddev(),
                  question.getVariance(), question.getMedian(), null, null, sQ, maxScore);
            } else
            {
              throw new SQLException("Max Score was not found.");
            }
          }
        }
        con.close();
      } else
      {
        con.close();
        System.err.println("Error! Input either schoolexams or teamexams.");
        System.exit(-1);
      }
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  private static boolean checkDatabaseForQuestions(Connection con, List<RoundOffStatsQuestion> rosqList)
  {
    int equalQuestions = 0;
    try
    {
      ResultSet rs = con.prepareStatement("SELECT * FROM stats_exams.questions").executeQuery();
      int k = 0;
      while (rs.next())
      {
        String question = rs.getString(2);
        double mean = Double.parseDouble(rs.getString(4));
        double median = Double.parseDouble(rs.getString(5));
        double stddev = Double.parseDouble(rs.getString(6));
        double variance = Double.parseDouble(rs.getString(7));
        double maxscore = Double.parseDouble(rs.getString(8));
        String q = Integer.toString(Integer.parseInt(rosqList.get(k++).getQuestion()) + 1);
        String sQ = "q".concat(q);
        if (question.equals(sQ) && mean >= 0 && median >= 0 && stddev >= 0 && variance >= 0 && maxscore >= 0)
        {
          equalQuestions++;
        }
      }
      return equalQuestions == ExamAnalysis.AMOUNT_OF_QUESTIONS;
    } catch (SQLException e)
    {
      System.out.println(e.getSQLState());
    }
    return false;
  }
}
