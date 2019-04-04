/*
 * Copyright (c) 2018-2019 Edwin Carlsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.bth.io.output;

import com.bth.analysis.stats.StatsSchool;
import com.bth.analysis.stats.StatsTeam;
import com.bth.analysis.stats.helperobjects.RoundOffStatsQuestion;
import com.bth.exams.ExamSchool;
import com.bth.exams.ExamTeam;
import com.bth.io.input.ExamInput;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ExamOutput {

  // This will be dynamically changed by the CSV input file.
  private static String directory = "/Users/edwincarlsson/Documents/Programmering/exam_Analysis/src/main/resources/data/output";

  public static void printToCSV_Teams(List<StatsTeam> teamList) {
    teamList.sort(Comparator.comparing(StatsTeam::getScore));

    try (PrintWriter writer = new PrintWriter(new File(directory + "/output_teams.csv"))) {
      writeHeaderWithPrintWriter(writer, false);

      for (StatsTeam team : teamList) {
        String str = getStringRepresentation(
            team.getScore(),
            team.getStddev(),
            team.getMean(),
            team.getMedian(),
            team.getVariance(),
            null,
            team);
        writer.write(str);
      }
      writeCopyrightAndAuthorInformation(writer);
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
    if (!questions) {
      writer.write(",Amount of exams for school");
    }
    writer.write("\n");
  }

  private static String getStringRepresentation(double score2, double stddev2, double mean2,
      double median2, double variance2, StatsSchool statsSchool, StatsTeam statsTeam) {
    StringBuilder builder = new StringBuilder();
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
    String amountOfQuestions = null;
    if (statsSchool != null) {
      amountOfQuestions =
          "," + (ExamInput.getSchoolIndex(statsSchool));
    }

    builder.append(name);
    builder.append(score);
    builder.append(mean);
    builder.append(median);
    builder.append(stddev);
    builder.append(variance);
    if (amountOfQuestions != null) {
      builder.append(amountOfQuestions);
    }
    builder.append('\n');

    return builder.toString();
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
      writeCopyrightAndAuthorInformation(writer);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void writeCopyrightAndAuthorInformation(PrintWriter writer) {
    DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    String sDate = format.format(date);
    writer.write(
        "Latest Update: " + LocalDate.now().toString() + " "
            + "by Edwin Carlsson, S.E.R.O. at clock: " + sDate);
  }

  public static void printToCSV_Schools(List<StatsSchool> statsSchools) {
    statsSchools.sort(Comparator.comparing(StatsSchool::getScore));
    try (PrintWriter writer = new PrintWriter(new File(directory + "/output_schools.csv"))) {
      writeHeaderWithPrintWriter(writer, false);
      for (StatsSchool statsSchool : statsSchools) {
        String string = getStringRepresentation(statsSchool.getScore(),
            statsSchool.getStddev(), statsSchool.getMean(), statsSchool.getMedian(),
            statsSchool.getVariance(), statsSchool, null);
        writer.write(string);
        System.out.println(ExamInput.getSchoolIndex(statsSchool));
      }
      writeCopyrightAndAuthorInformation(writer);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void printQuestionsToCSV(ExamSchool[] exams, ExamTeam[] teams) {
    // Sort according to hashCode, so that they indices are aligned for the printing.
    List<ExamSchool> sortSchoolExams = Arrays.asList(exams);
    List<ExamTeam> sortTeamExams = Arrays.asList(teams);
    sortSchoolExams.sort(Comparator.comparingInt(o -> o.getExam().hashCode()));
    sortTeamExams.sort(Comparator.comparingInt(o -> o.getExam().hashCode()));
    exams = sortSchoolExams.toArray(new ExamSchool[0]);
    teams = sortTeamExams.toArray(new ExamTeam[0]);
    // End sort.

    try (PrintWriter writer = new PrintWriter(directory + "/output_onetofourteenandschools.csv")) {
      writer.write("School,");
      writer.write("Exam Anon Code,");
      writer.write("Team Name,");

      for (int i = 1; i <= 14; i++) {
        if (i < 14) {
          writer.write("q" + i + ",");
        } else {
          writer.write("q" + i);
        }
      }
      writer.write("\n");

      for (int i = 0; i < exams.length; i++) {
        ExamSchool schools = exams[i];
        ExamTeam team = teams[i];

        boolean examsAreEqual = false;
        if (schools.getExam().hashCode() == team.getExam().hashCode()) {
          examsAreEqual = true;
        }
        double[] scores = schools.getSeparateScoresForAllQuestions();
        writer.write(schools.getSchool() + ",");
        writer.write(schools.getExam().getAnonymousCode() + ",");
        if (examsAreEqual) {
          writer.write(team.getTeam() + ",");
        } else {
          writer.write("Null,");
        }
        for (int j = 0; j < scores.length; j++) {
          writer.write("" + scores[j]);
          if (j != 13) {
            writer.write(",");
          }
        }
        writer.write("\n");
      }
      writeCopyrightAndAuthorInformation(writer);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static String getDirectory() {
    return directory;
  }

  public static void setDirectory(String directory) {
    ExamOutput.directory = directory;
  }
}
