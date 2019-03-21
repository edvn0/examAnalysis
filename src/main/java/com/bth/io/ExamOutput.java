package com.bth.io;

import com.bth.analysis.Stats.StatsSchool;
import com.bth.analysis.Stats.StatsTeam;
import com.bth.analysis.Stats.helperobjects.RoundOffStatsQuestion;
import com.bth.exams.ExamSchool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

class ExamOutput {
  private static final String directory = "/Users/edwincarlsson/Documents/Programmering/Java-programmering/src/main/java/com.bth/data/output";

  public static void printToCSV_Teams(List<StatsTeam> teamList) {
    teamList.sort(Comparator.comparing(StatsTeam::getScore));

    try (PrintWriter writer = new PrintWriter(new File(directory + "/output_teams.csv"))) {
      writeHeaderWithPrintWriter(writer, false);

      for (StatsTeam team : teamList) {
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
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void writeHeaderWithPrintWriter(PrintWriter writer, boolean questions) {
    if (!questions) {
      writer.write("Name,");
      writer.write("Score,");
    } else {
      writer.write("Question,");
    }
    writer.write("Mean,");
    writer.write("Median,");
    writer.write("Standard Deviation,");
    writer.write("Variance");
    if (questions) {
      writer.write(",");
      writer.write("Max Score");
    }
    writer.write("\n");
  }

  private static void getStringRepresentation(PrintWriter writer, StringBuilder stringBuilder, double score2, double stddev2, double mean2, double median2, double variance2, StatsSchool statsSchool, StatsTeam statsTeam) {
    String name = "";
    if (statsSchool == null) {
      name += statsTeam.getTeam();
    } else if (statsTeam == null) {
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

  public static void printToCSV_Questions(List<RoundOffStatsQuestion> questions) {
    try (PrintWriter writer = new PrintWriter(new File(directory + "/output_questions.csv"))) {
      writeHeaderWithPrintWriter(writer, true);

      for (RoundOffStatsQuestion rosQ : questions) {
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
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void printToCSV_Schools(List<StatsSchool> statsSchools) {
    statsSchools.sort(Comparator.comparing(StatsSchool::getScore));

    try (PrintWriter writer = new PrintWriter(new File(directory + "/output_schools.csv"))) {
      writeHeaderWithPrintWriter(writer, false);

      for (StatsSchool statsSchool : statsSchools) {
        StringBuilder stringBuilder = new StringBuilder();
        getStringRepresentation(writer, stringBuilder, statsSchool.getScore(), statsSchool.getStddev(), statsSchool.getMean(), statsSchool.getMedian(), statsSchool.getVariance(), statsSchool, null);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void printQuestionsToCSV(ExamSchool[] statsSchools) {
    // Headers:
    /*
     * school:
     * questions = [1,2,3,4,5,6,7,8,9,10,11,12,13,14]
     */
    try (PrintWriter writer = new PrintWriter(directory + "/output_onetofourteenandschools.csv")) {
      writer.write("School,");
      for (int i = 1; i <= 14; i++) {
        if (i < 14)
          writer.write("q" + i + ",");
        else
          writer.write("q" + i);
      }
      writer.write("\n");

      for (ExamSchool schools : statsSchools) {
        double[] scores = schools.getSeparateScoresForAllQuestions();
        writer.write(schools.getSchool() + ",");
        for (int i = 0; i < scores.length; i++) {
          writer.write("" + scores[i]);
          if (i != 13) writer.write(",");
        }
        writer.write("\n");
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }


  }
}