package io;

import analysis.Stats.StatsSchool;
import analysis.Stats.StatsTeam;
import analysis.Stats.helperobjects.RoundOffStatsQuestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class ExamOutput
{
  private static String directory = "/Users/edwincarlsson/Library/Mobile Documents/com~apple~CloudDocs/Java-programmering/src/data/output";

  public static void printToCSV_Teams(List<StatsTeam> teamList)
  {
    teamList.sort(Comparator.comparing(StatsTeam::getScore));

    try (PrintWriter writer = new PrintWriter(new File(directory + "/output_teams.csv")))
    {
      writeHeaderWithPrintWriter(writer);

      for (StatsTeam team : teamList)
      {
        StringBuilder stringBuilder = new StringBuilder();
        getStringRepresentation(writer, stringBuilder, team.getScore(), team.getStddev(), team.getMean(), team.getMedian(), team.getVariance(), null, team);
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
      writeHeaderWithPrintWriter(writer);

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

  private static void writeHeaderWithPrintWriter(PrintWriter writer)
  {
    writer.write("Name,");
    writer.write("Score,");
    writer.write("Mean,");
    writer.write("Median,");
    writer.write("Standard Deviation,");
    writer.write("Variance");
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

  private static void setStatementWithPS(PreparedStatement statement, double score, double mean, double stddev, double variance, double median, StatsTeam team, StatsSchool school, String question) throws SQLException
  {
    if (question != null && school == null && team == null)
    {
      statement.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
      statement.setString(2, question);
      statement.setString(3, "" + mean);
      statement.setString(4, "" + median);
      statement.setString(5, "" + stddev);
      statement.setString(6, "" + variance);
    } else if (team == null)
    {
      statement.setString(1, school.getSchool());
      statement.setString(2, "" + score);
      statement.setString(3, "" + mean);
      statement.setString(4, "" + stddev);
      statement.setString(5, "" + variance);
      statement.setString(6, "" + median);
    } else if (school == null)
    {
      statement.setString(1, team.getTeam());
      statement.setString(2, "" + score);
      statement.setString(3, "" + mean);
      statement.setString(4, "" + stddev);
      statement.setString(5, "" + variance);
      statement.setString(6, "" + median);
    }
    statement.execute();
  }

  public static void insertIntoDatabase(List<StatsTeam> teamList, List<StatsSchool> schoolList, List<RoundOffStatsQuestion> rosqList)
  {
    try
    {
      Connection con = DriverManager.getConnection(
          "jdbc:mysql://localhost:8889/stats_exams",
          "Edwin",
          "Edwin98");

      if (teamList == null && schoolList != null && rosqList == null)
      {
        for (StatsSchool school : schoolList)
        {
          String sql = "insert into stats_exams.school_statistics (name,score,mean,standarddev,variance,median) values (?,?,?,?,?,?)";
          PreparedStatement statement = con.prepareStatement(sql);
          setStatementWithPS(statement, school.getScore(),
              school.getMean(), school.getStddev(),
              school.getVariance(), school.getMedian(), null, school, null);
        }
        con.close();
      } else if (schoolList == null && teamList != null && rosqList == null)
      {
        for (StatsTeam team : teamList)
        {
          String sql = "insert into stats_exams.team_statistics (name,score,mean,standarddev,variance,median) values (?,?,?,?,?,?)";
          PreparedStatement statement = con.prepareStatement(sql);
          setStatementWithPS(statement, team.getScore(),
              team.getMean(), team.getStddev(),
              team.getVariance(), team.getMedian(), team, null, null);
        }
        con.close();
      } else if (schoolList == null && teamList == null && rosqList != null)
      {
        for (RoundOffStatsQuestion question : rosqList)
        {
          String q = Integer.toString(Integer.parseInt(question.getQuestion()) + 1);
          String sQ = "q".concat(q);
          String table = "stats_exams.".concat(sQ);
          String sql = "insert into " + table + " (date, question, mean, median, stddev, variance) values (?,?,?,?,?,?)";

          PreparedStatement statement = con.prepareStatement(sql);

          setStatementWithPS(statement, 0,
              question.getMean(), question.getStddev(),
              question.getVariance(), question.getMedian(), null, null, sQ);
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
}
