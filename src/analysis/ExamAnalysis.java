package analysis;


import Exams.Exam;
import Exams.ExamSchool;
import Exams.ExamTeam;
import io.ExamInput;

import java.util.List;

public class ExamAnalysis
{
  public final static int AMOUNT_OF_QUESTIONS = 16;

  private ExamInput input;
  private ExamTeam[] examTeams;
  private ExamSchool[] examSchools;
  private List<ExamSchool[]> examSchoolList;
  private List<ExamTeam[]> examTeamList;
  private Exam[] exams;

  private String dir;

  public ExamAnalysis(String dir)
  {
    this.dir = dir;
  }

  private void init()
  {
    this.examSchools = input.getExamSchools();
    this.examTeams = input.getExamTeams();

    // This is a data structure with all the exams associated via school.
    this.examSchoolList = input.getExamSchoolArrayBySchool();

    // This is a data structure with all the exams associated via code
    this.examTeamList = input.getExamTeamArrayByTeams();

    for (ExamSchool es : examSchools)
    {
      String name = es.getSchool();
      analyseExams(this.examSchoolList, name);
    }
  }

  // Statistical Analysis

  // Exams for all schools.
  // Loop through all "rows" of the list, then loop through all the exams, then all questions.
  // Add into object StatsSchool, as a new List.
  protected void analyseExams(List<ExamSchool[]> exams, String school)
  {
    double standardDeviation, mean, median;
    int totLength = AMOUNT_OF_QUESTIONS;

    System.out.println("Total score! " + input.getTotalScore(school));

    double sum = 0;
    for (ExamSchool[] el : exams)
    {
      if (el[0].getSchool().toLowerCase().trim().equals(school.toLowerCase().trim()))
        sum += input.getTotalScore(school);
    }

    mean = sum / totLength;


    double squaredSum = 0;
    for (ExamSchool[] el : exams)
    {
      if (el[0].getSchool().toLowerCase().trim().equals(school.toLowerCase().trim()))
        squaredSum += Math.pow(input.getTotalScore(school), 2);
    }

    double varianceSquared = (squaredSum / totLength) - Math.pow(mean, 2);

  }

  // Exams for specific schools
  protected static void analyseExams(String school, ExamSchool[] schools)
  {
    int standardDeviation, mean, median;
  }

  // Exams for all the teams.
  protected static void analyseExams(ExamTeam[] exams)
  {
    int standardDeviation, mean, median;
  }

  // Exams for specific team.
  protected static void analyseExams(int team, ExamTeam[] teams)
  {
    int standardDeviation, mean, median;
  }

  // End statistical Analysis

  // Mutators

  public ExamInput getInput()
  {
    return input;
  }

  public void setInput(ExamInput input)
  {
    this.input = input;
  }

  public ExamTeam[] getExamTeams()
  {
    return examTeams;
  }

  public void setExamTeams(ExamTeam[] examTeams)
  {
    this.examTeams = examTeams;
  }

  public ExamSchool[] getExamSchools()
  {
    return examSchools;
  }

  public void setExamSchools(ExamSchool[] examSchools)
  {
    this.examSchools = examSchools;
  }

  public Exam[] getExams()
  {
    return exams;
  }

  public void setExams(Exam[] exams)
  {
    this.exams = exams;
  }

  public List<ExamSchool[]> getExamSchoolList()
  {
    return examSchoolList;
  }

  public void setExamSchoolList(List<ExamSchool[]> examSchoolList)
  {
    this.examSchoolList = examSchoolList;
  }

  public List<ExamTeam[]> getExamTeamList()
  {
    return examTeamList;
  }

  public void setExamTeamList(List<ExamTeam[]> examTeamList)
  {
    this.examTeamList = examTeamList;
  }

  public void start()
  {
    input = new ExamInput(this.dir);
    init();
  }
}
