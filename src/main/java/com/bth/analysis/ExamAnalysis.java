package com.bth.analysis;

import com.bth.analysis.stats.StatsSchool;
import com.bth.analysis.stats.StatsTeam;
import com.bth.analysis.stats.helperobjects.RoundOffStats;
import com.bth.analysis.stats.helperobjects.RoundOffStatsQuestion;
import com.bth.exams.ExamSchool;
import com.bth.exams.ExamTeam;
import com.bth.gui.controller.loginusers.SQLLoginUser;
import com.bth.io.input.ExamInput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.DoubleStream;

public class ExamAnalysis {

  private final int amountOfQuestions = 14;
  private final String dir;
  private ExamInput input;
  private List<StatsTeam> statsTeams;
  private List<StatsSchool> statsSchools;
  private List<RoundOffStatsQuestion> questionsStats;
  private ExamTeam[] examTeams;
  private ExamSchool[] examSchools;

  private SQLLoginUser user;

  public ExamAnalysis() {
    this.dir = null;
  }

  public void setUser(SQLLoginUser user) {
    this.user = user;
  }

  public ExamInput getInput() {
    return input;
  }

  public void setInput(ExamInput input) {
    this.input = input;
  }

  public void startWithDatabase() {
    input = new ExamInput(user);
    init();
  }

  private void init() {
    examSchools = input.getExamSchools();
    examTeams = input.getExamTeams();

    statsTeams = this.generateStatsTeams(examTeams);
    statsSchools = this.generateStatsSchools(examSchools);
    questionsStats = this.generateQuestionsStats();
  }

  //////////////////////////////
  //// Statistical Analysis ////
  //////////////////////////////

  // java.exams for a specific team.
  private StatsTeam analyseExams(ExamTeam[] exams, String name) {
    double sum;
    ExamTeam teamSameName = this.getExamTeamFromName(exams, name);
    boolean isNull = teamSameName == null;

    double[] averageScores = new double[amountOfQuestions];

    if (!isNull) {
      for (int i = 0; i < teamSameName.getSeparateScoresForAllQuestions().length; i++) {
        averageScores[i] = teamSameName.getSeparateScoresForAllQuestions()[i];
      }
    }

    // MEAN
    sum = Arrays.stream(averageScores).sum();
    double mean = sum / amountOfQuestions;

    // Variance
    double variance = generateVariance(averageScores, mean, amountOfQuestions);

    // STDDEV
    double standardDeviation = generateStandardDeviation(averageScores, mean, amountOfQuestions);

    // Median
    double median = generateMedian(averageScores);

    // TOTAL SCORE
    assert teamSameName != null;
    double totalScore = teamSameName.getScore();

    // Round off all values into an object for refactoring.
    RoundOffStats roundOffStats = this.getRoundOffObject(mean, median, standardDeviation, variance);

    return new StatsTeam(name, totalScore, roundOffStats);
  }

  // Exams for a specific school.
  private StatsSchool analyseExams(ExamSchool[] schools, String name) {
    double sum = 0;
    final int totLength = amountOfQuestions;

    ExamSchool[] schoolsWithSameName = this.getExamSchoolArrayFromName(schools, name);
    final int arrayLength = schoolsWithSameName.length;

    double[] averageScores = new double[totLength];
    double averageScore;

    // This makes an average score array from the full school, to do stats java.analysis on.
    for (ExamSchool examSchool : schoolsWithSameName) {
      for (int j = 0; j < totLength; j++) {
        double[] sepscores = examSchool.getSeparateScoresForAllQuestions();
        averageScores[j] += sepscores[j];
      }
    }

    DoubleStream stream = Arrays.stream(averageScores);
    averageScore = stream.sum();

    for (int i = 0; i < averageScores.length; i++) {
      averageScores[i] = averageScores[i] / arrayLength;
    }
    averageScore = averageScore / arrayLength;

    // MEAN
    for (int i = 0; i < totLength; i++) {
      sum += averageScores[i];
    }
    double mean = sum / totLength;

    // VARIANCE
    double variance = this.generateVariance(averageScores, mean, totLength);

    // Standard deviation
    double standardDeviation = this.generateStandardDeviation(averageScores, mean, totLength);

    // Median
    double median = generateMedian(averageScores);

    RoundOffStats ros = new RoundOffStats(mean, median, standardDeviation, variance, 1000.00);

    return new StatsSchool(name, averageScore, ros.getMean(), ros.getMedian(), ros.getStddev(),
        ros.getVariance());
  }

  // Uses analyseExams to analyse ALL teams.
  private List<StatsTeam> generateStatsTeams(ExamTeam[] teams) {
    Set<StatsTeam> teamSet = new HashSet<>();
    for (ExamTeam team : teams) {
      StatsTeam statsTeam = analyseExams(teams, team.getTeam());
      teamSet.add(statsTeam);
    }
    return new ArrayList<>(teamSet);
  }

  // Uses analyseExams to analyse ALL schools.
  private List<StatsSchool> generateStatsSchools(ExamSchool[] schools) {
    Set<StatsSchool> teamSet = new HashSet<>();
    for (ExamSchool school : schools) {
      StatsSchool statsSchool = analyseExams(schools, school.getSchool());
      teamSet.add(statsSchool);
    }
    return new ArrayList<>(teamSet);
  }

  //////////////////////////////////
  //// End Statistical Analysis ////
  //////////////////////////////////

  // Helper object
  private RoundOffStats getRoundOffObject(double mean, double median, double stddev,
      double variance) {
    return new RoundOffStats(mean, median, stddev, variance, 1000.0);
  }

  private RoundOffStatsQuestion getRoundOffQuestionsObject(String question, double mean,
      double median, double stddev, double variance) {
    return new RoundOffStatsQuestion(mean, median, stddev, variance, 1000.00, question);
  }

  private List<RoundOffStatsQuestion> generateQuestionsStats() {
    List<RoundOffStatsQuestion> stats = new ArrayList<>();

    for (int i = 0; i < amountOfQuestions; i++) {
      RoundOffStatsQuestion question = this.generateRoundOffStats(i, examTeams);
      stats.add(question);
    }

    return stats;
  }

  private RoundOffStatsQuestion generateRoundOffStats(int index, ExamTeam[] examTeams) {
    double[] scoresForIndex = new double[examTeams.length];
    for (int i = 0; i < scoresForIndex.length; i++) {
      scoresForIndex[i] = examTeams[i].getSeparateScoresForAllQuestions()[index];
    }

    double mean = generateMean(scoresForIndex, scoresForIndex.length);
    double median = generateMedian(scoresForIndex);
    double stddev = generateStandardDeviation(scoresForIndex, mean, scoresForIndex.length);
    double variance = generateVariance(scoresForIndex, mean, scoresForIndex.length);

    return getRoundOffQuestionsObject(String.valueOf((index + 1)), mean, median, stddev, variance);
  }

  // Helper function which gets an array with all the schools sharing a name.
  private ExamSchool[] getExamSchoolArrayFromName(ExamSchool[] schools, String name) {
    ExamSchool[] schoolArray = null;
    if (name != null) {
      int size = 0;
      for (ExamSchool es : schools) {
        if (es.getSchool().trim().toLowerCase().equals(name.trim().toLowerCase())) {
          size++;
        }
      }

      schoolArray = new ExamSchool[size];

      int k = 0;
      for (ExamSchool es : schools) {
        if (es.getSchool().trim().toLowerCase().equals(name.trim().toLowerCase())) {
          schoolArray[k++] = es;
        }
      }
    }
    return schoolArray;
  }

  // Helper function which gets an array with all the teams sharing a name.
  private ExamTeam getExamTeamFromName(ExamTeam[] exams, String name) {
    ExamTeam teamsWithSameName = null;
    if (name != null) {
      for (ExamTeam et : exams) {
        if (et.getTeam().toLowerCase().trim().equals(name.toLowerCase().trim())) {
          teamsWithSameName = new ExamTeam(et.getExam(), et.getTeam());
        }
      }
    }
    return teamsWithSameName;
  }
  // End helper object

  // Help math
  public double generateMean(double[] array, int length) {
    double sum = 0;
    for (double d : array) {
      sum += d;
    }
    return sum / length;
  }

  public double generateMedian(double[] array) {
    Arrays.sort(array);
    double midPoint = ((array[(array.length - 1) / 2] + array[array.length / 2]) / 2);
    double midPointMid = array[(array.length - 1) / 2];
    return array.length % 2 == 0 ? midPoint : midPointMid;
  }

  public double generateVariance(double[] scores, double mean, int length) {
    return getXiSquared(scores, mean) / length;
  }

  public double generateStandardDeviation(double[] averageScores, double mean, int totLength) {
    double xiXbarSum = getXiSquared(averageScores, mean);
    return Math.sqrt(xiXbarSum / totLength);
  }

  private double getXiSquared(double[] averageScores, double mean) {
    double xiXbarSum = 0;
    for (double xi : averageScores) {
      double meanDiff = xi - mean;
      xiXbarSum += meanDiff * meanDiff;
    }
    return xiXbarSum;
  }
  // End help math

  // Getters
  public List<StatsTeam> getStatsTeams() {
    return statsTeams;
  }

  public List<StatsSchool> getStatsSchools() {
    return statsSchools;
  }

  public List<RoundOffStatsQuestion> getQuestionsStats() {
    return questionsStats;
  }

  public int getSchoolIndex(StatsSchool school) {
    return this.input.getSchoolIndex(school);
  }

  public int getQuestionMaxScore(RoundOffStatsQuestion question) {
    System.out.println(question.getQuestion());
    return this.input.getMaxScore(question.getQuestion());
  }

  // End getters

}
