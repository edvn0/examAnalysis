package io;

import analysis.Stats.StatsSchool;
import analysis.Stats.StatsTeam;

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

  public static void insertIntoDatabaseTeams(List<StatsTeam> teamList)
  {
    try
    {
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8889/stats_exams", "Edwin", "Edwin98");

      for (StatsTeam team : teamList)
      {
        String sql = "insert into stats_exams.team_statistics (name,score,mean,standarddev,variance,median) values (?,?,?,?,?,?)";
        PreparedStatement statement = con.prepareStatement(sql);
        setStatementWithPS(statement, team.getScore(), team.getMean(), team.getStddev(), team.getVariance(), team.getMedian(), team, null);
      }
    } catch (Exception e)
    {
      e.printStackTrace();
    }

  }

  private static void setStatementWithPS(PreparedStatement statement, double score, double mean, double stddev, double variance, double median, StatsTeam team, StatsSchool school) throws SQLException
  {
    if (school == null)
    {
      statement.setString(1, team.getTeam());
    } else if (team == null)
    {
      statement.setString(1, school.getSchool());
    }
    statement.setString(2, "" + score);
    statement.setString(3, "" + mean);
    statement.setString(4, "" + stddev);
    statement.setString(5, "" + variance);
    statement.setString(6, "" + median);
    statement.execute();
  }

  public static void insertIntoDatabaseSchools(List<StatsSchool> teamList)
  {
    try
    {
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8889/stats_exams", "Edwin", "Edwin98");

      for (StatsSchool school : teamList)
      {
        String sql = "insert into stats_exams.school_statistics (name,score,mean,standarddev,variance,median) values (?,?,?,?,?,?)";
        PreparedStatement statement = con.prepareStatement(sql);
        setStatementWithPS(statement, school.getScore(), school.getMean(), school.getStddev(), school.getVariance(), school.getMedian(), null, school);
      }
    } catch (Exception e)
    {
      e.printStackTrace();
    }

  }

}
