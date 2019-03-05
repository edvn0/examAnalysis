package analysis;


import Exams.Exam;
import Exams.ExamSchool;
import Exams.ExamTeam;
import analysis.Stats.StatsSchool;
import analysis.Stats.StatsTeam;
import analysis.Stats.helperobjects.RoundOffStats;
import io.ExamInput;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ExamAnalysis
{
  private final static int AMOUNT_OF_QUESTIONS = 16;

  private ExamInput input;
  private Exam[] exams;

  private List<StatsTeam> statsTeams;
  private List<StatsSchool> statsSchools;

  private final String dir;

  public ExamAnalysis(String dir)
  {
    this.dir = dir;
  }

  public void start()
  {
    input = new ExamInput(this.dir);
    init();
  }

  private void init()
  {
    ExamSchool[] examSchools = input.getExamSchools();
    ExamTeam[] examTeams = input.getExamTeams();

    // This is a data structure with all the exams associated via school.
    List<ExamSchool[]> examSchoolList = input.getExamSchoolArrayBySchool();

    // This is a data structure with all the exams associated via code
    List<ExamTeam[]> examTeamList = input.getExamTeamArrayByTeams();

    statsTeams = this.generateStatsTeams(examTeams);
    statsSchools = this.generateStatsSchools(examSchools);
  }

  // Statistical Analysis
  // Exams for a specific team.
  private StatsTeam analyseExams(@NotNull ExamTeam[] exams, @NotNull String name)
  {
    double standardDeviation, mean;
    double sum;
    int totLength = AMOUNT_OF_QUESTIONS;
    double squaredSum = 0;
    double variance;
    ExamTeam teamSameName = this.getExamTeamFromName(exams, name);

    // Every examteam[i] has an array with scores[j]. Average scores is a combined array with averages of every
    // question for every team. This is easier to work with and to analyse.
    double[] average_scores = new double[totLength];
    for (int i = 1; i < teamSameName.getSeparateScoresForAllQuestions().length; i++)
    {
      int index = i - 1;
      average_scores[index] = teamSameName.getSeparateScoresForAllQuestions()[i];
    }

    // MEAN
    sum = Arrays.stream(average_scores).sum();
    mean = sum / totLength;

    // Variance
    for (double average_score1 : average_scores)
    {
      double xi_xbar = average_score1 - mean;
      squaredSum += xi_xbar * xi_xbar;
    }
    variance = (squaredSum / (totLength));

    // STDDEV
    standardDeviation = getStandardDeviation(mean, totLength, average_scores);

    // Median
    Arrays.sort(average_scores);
    double median = average_scores[(average_scores.length - 1) / 2];

    // TOTAL SCORE
    int totalScore = teamSameName.getScore();

    // Round off all values into an object for refactoring.
    RoundOffStats roundOffStats = this.getRoundOffObject(mean, median, standardDeviation, variance, 1000.0);

    return new StatsTeam(name, totalScore, roundOffStats.getMean(), roundOffStats.getMedian(), roundOffStats.getStddev(), roundOffStats.getVariance());
  }

  // Exams for a specific school.
  private StatsSchool analyseExams(@NotNull ExamSchool[] schools, String name)
  {
    double standardDeviation, mean, variance;
    double sum = 0;
    final int totLength = AMOUNT_OF_QUESTIONS;
    double squaredSum = 0;

    ExamSchool[] schoolsWithSameName = this.getExamSchoolArrayFromName(schools, name);
    final int arrayLength = schoolsWithSameName.length;

    double[] average_scores = new double[totLength];
    double average_score = 0;

    for (int i = 0; i < arrayLength; i++)
    {
      average_score += schoolsWithSameName[i].getScore();
    }
    average_score /= arrayLength;

    // This makes an average score array for the full school, to do stats analysis on.
    for (ExamSchool examSchool : schoolsWithSameName)
    {
      for (int j = 0; j < totLength; j++)
      {
        int[] sepscores = examSchool.getSeparateScoresForAllQuestions();
        average_scores[j] += (double) sepscores[j];
      }
    }
    for (int i = 0; i < average_scores.length; i++)
    {
      average_scores[i] = average_scores[i] / arrayLength;
    }

    // MEAN
    for (int i = 0; i < totLength; i++)
    {
      sum += average_scores[i];
    }
    mean = sum / totLength;

    // VARIANCE
    variance = this.produceVariance(average_scores, mean, totLength);

    // Standard deviation
    standardDeviation = getStandardDeviation(mean, totLength, average_scores);

    // Median
    Arrays.sort(average_scores);
    double median = arrayLength % 2 == 0 ? average_scores[(totLength - 1) / 2] : (average_scores[(totLength - 1) / 2] + average_scores[totLength / 2]) / 2.0;

    RoundOffStats ros = new RoundOffStats(mean, median, standardDeviation, variance, 1000.00);

    return new StatsSchool(name, average_score, ros.getMean(), ros.getMedian(), ros.getStddev(), ros.getVariance());
  }

  // Helper function which gets an array with all the teams sharing a name.
  private ExamTeam getExamTeamFromName(ExamTeam[] exams, String name)
  {
    if (name != null)
    {
      ExamTeam teamsWithSameName = null;
      for (ExamTeam et : exams)
      {
        if (et.getTeam().toLowerCase().trim().equals(name.toLowerCase().trim()))
          teamsWithSameName = new ExamTeam(et.getExam(), et.getTeam());
      }
      return teamsWithSameName;
    } else
    {
      return null;
    }
  }

  // Helper function which gets an array with all the schools sharing a name.
  private ExamSchool[] getExamSchoolArrayFromName(ExamSchool[] schools, String name)
  {
    if (name != null)
    {
      int size = 0;
      for (ExamSchool es : schools)
      {
        if (es.getSchool().trim().toLowerCase().equals(name.trim().toLowerCase()))
        {
          size++;
        }
      }
      ExamSchool[] schoolArray = new ExamSchool[size];

      int k = 0;
      for (ExamSchool es : schools)
      {
        if (es.getSchool().trim().toLowerCase().equals(name.trim().toLowerCase()))
        {
          schoolArray[k++] = new ExamSchool(name, es.getExam());
        }
      }

      return schoolArray;
    } else
    {
      return null;
    }
  }

  // Uses analyseExams to analyse ALL teams.
  private List<StatsTeam> generateStatsTeams(ExamTeam[] teams)
  {
    Set<StatsTeam> teamSet = new HashSet<>();
    for (ExamTeam team : teams)
    {
      StatsTeam statsTeam = analyseExams(teams, team.getTeam());
      teamSet.add(statsTeam);
    }

    return new ArrayList<>(teamSet);
  }

  // Uses analyseExams to analyse ALL schools.
  private List<StatsSchool> generateStatsSchools(ExamSchool[] schools)
  {
    Set<StatsSchool> teamSet = new HashSet<>();
    for (ExamSchool school : schools)
    {
      StatsSchool statsSchool = analyseExams(schools, school.getSchool());
      teamSet.add(statsSchool);
    }

    return new ArrayList<>(teamSet);
  }
  // End statistical Analysis

  // Helper object
  private RoundOffStats getRoundOffObject(double mean, double median, double stddev, double variance, double numOfDec)
  {
    return new RoundOffStats(mean, median, stddev, variance, numOfDec);
  }
  // End helper object

  // Help math
  private double produceVariance(double[] scores, double mean, int totLength)
  {

    return getXiSquared(mean, scores) / totLength;
  }

  private double getStandardDeviation(double mean, int totLength, double[] average_scores)
  {
    double xi_xbar_sum = getXiSquared(mean, average_scores);

    return Math.sqrt(xi_xbar_sum / totLength);

  }

  private double getXiSquared(double mean, double[] average_scores)
  {
    double xi_xbar_sum = 0;
    for (double xi : average_scores)
    {
      double xi_xbar = xi - mean;
      xi_xbar_sum += xi_xbar * xi_xbar;
    }
    return xi_xbar_sum;
  }
  // End help math

  // Getters
  public List<StatsTeam> getStatsTeams()
  {
    return statsTeams;
  }

  public List<StatsSchool> getStatsSchools()
  {
    return statsSchools;
  }
  // End getters

}
