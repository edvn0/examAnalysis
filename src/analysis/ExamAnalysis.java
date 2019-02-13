package analysis;


import Exams.Exam;
import Exams.ExamSchool;
import Exams.ExamTeam;
import io.ExamInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamAnalysis
{
  private final String[] SCHOOLS = {"BEST", "Cefyrekon", "EKEN", "Ekonomisektionen", "Esekon", "Eurekha", "Gavlecon"
      , "HHGS", "IMF", "Kalmar ESS", "KarlEkon", "Mälekon", "Novitas", "Safir", "Sesam", "SköEkon",
      "Sundekon", "SÖFRE", "VisEkon", "VästEko"};

  private int[] CODES;

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
    this.exams = this.input.getExams();
    this.examSchools = this.input.getExamSchools();
    this.examTeams = input.getExamTeams();

    this.CODES = this.input.getExamCodes();
    Arrays.sort(this.CODES);

    // This is a data structure with all the exams associated via school.
    this.examSchoolList = getExamSchoolArrayBySchool();

    // This is a data structure with all the exams associated via code
    this.examTeamList = getExamTeamArrayByTeams();

    double stdDev = this.analyseExams(exams);
    System.out.println(stdDev);
  }

  /*
  Retrieve an array like so:
  arr[0] = Alla tentor som associeras med BEST.
  arr[1] = Alla tentor som associeras med Cefyrekon
  arr[n] = Alla tentor som associeras med SCHOOLS[n]
  */
  private List<ExamSchool[]> getExamSchoolArrayBySchool()
  {
    List<ExamSchool[]> examSchools = new ArrayList<>();

    for (String school : SCHOOLS)
    {
      ExamSchool[] loopArray = this.getExamSchoolsBySchool(school);
      examSchools.add(loopArray);
    }

    return examSchools;
  }

  // Helper function to get the ExamSchool[] with all exams associated with input school.
  private ExamSchool[] getExamSchoolsBySchool(String school)
  {
    int size = 0;
    for (ExamSchool examSchool : this.examSchools)
    {
      if (examSchool.getSchool().toLowerCase().trim().equals(school.toLowerCase().trim()))
      {
        size++;
      }
    }

    ExamSchool[] examSchools = new ExamSchool[size];

    int k = 0;
    for (ExamSchool examSchool : this.examSchools)
    {
      if (examSchool.getSchool().toLowerCase().trim().equals(school.toLowerCase().trim()))
      {
        examSchools[k++] = new ExamSchool(school.toLowerCase().trim(), examSchool);
      }
    }
    return examSchools;
  }

  /*
  Retrieve an array like so:
  arr[0] = Alla tentor som associeras med 101
  arr[1] = Alla tentor som associeras med 1103
  arr[n] = Alla tentor som associeras med codes[n]
  */
  private List<ExamTeam[]> getExamTeamArrayByTeams()
  {
    List<ExamTeam[]> list = new ArrayList<>();

    for (int i : this.CODES)
    {
      ExamTeam[] loopArray = this.getExamTeamsByTeam(i);
      list.add(loopArray);
    }

    return list;
  }

  // Helper function to get the ExamTeams[] with all exams associated with input code.
  private ExamTeam[] getExamTeamsByTeam(int code)
  {
    int size = 0;
    for (ExamTeam examTeam : this.examTeams)
    {
      if (examTeam.getAnonymousCode() == code)
      {
        size++;
      }
    }

    ExamTeam[] teams = new ExamTeam[size];

    int k = 0;
    for (ExamTeam e : this.examTeams)
    {
      if (e.getAnonymousCode() == code)
      {
        teams[k++] = new ExamTeam(e.getExam(), code);
      }
    }
    return teams;
  }

  // Statistical Analysis

  // All the exams.
  private double analyseExams(Exam[] exams)
  {
    double standardDeviation = 0, mean = 0, median = 0;
    int length = exams.length;
    double sum = 0;

    for (int i = 0; i < length; i++)
    {
      for (int j = 0; j < 15; j++)
      {
        System.out.println(exams[i].getAnonymousCode() + " " + Arrays.toString(exams[i].getSeparateScoresForAllQuestions()));
        sum += (double) exams[i].getSeparateScoresForAllQuestions()[j];
      }
    }

    mean = sum / length;

    for (int i = 0; i < length; i++)
    {
      for (int j = 4; j < 19; j++)
      {
        standardDeviation += Math.pow((exams[i].getSeparateScoresForAllQuestions()[j] - mean), 2);
      }
    }

    return Math.sqrt(standardDeviation / length);
  }

  // Exams for all schools.
  private void analyseExams(ExamSchool[] exams)
  {
    int standardDeviation, mean, median;
  }

  // Exams for specific schools
  private void analyseExams(String school, ExamSchool[] schools)
  {
    int standardDeviation, mean, median;
  }

  // Exams for all the teams.
  private void analyseExams(ExamTeam[] exams)
  {
    int standardDeviation, mean, median;
  }

  // Exams for specific team.
  private void analyseExams(int team, ExamTeam[] teams)
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
