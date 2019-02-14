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

  public ExamAnalysis(String dir)
  {
    input = new ExamInput(dir);
    init();
  }

  private void init()
  {
    this.examSchools = input.getExamSchools();
    this.examTeams = input.getExamTeams();

    // This is a data structure with all the exams associated via school.
    this.examSchoolList = input.getExamSchoolArrayBySchool();

    // This is a data structure with all the exams associated via code
    this.examTeamList = input.getExamTeamArrayByTeams();
  }

  // Statistical Analysis

  // Exams for all schools.
  // Loop through all "rows" of the list, then loop through all the exams, then all questions.
  // Add into object StatsSchool, as a new List.
  protected static void analyseExams(List<ExamSchool[]> exams)
  {
    int standardDeviation, mean, median;
    int totLength = AMOUNT_OF_QUESTIONS;
    double sum = 0;

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
}
