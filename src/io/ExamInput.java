package io;

import Exams.Exam;
import Exams.ExamSchool;
import Exams.ExamTeam;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class ExamInput
{
  private Exam[] exams;
  private ExamTeam[] examTeams;
  private ExamSchool[] examSchools;

  private List<String[]> listOfRowsInData;
  private String[] teams;

  private static String[] SCHOOLS;

  private static final int INDIVIDUAL_SCORES_START = 5;
  private static final int INDIVIDUAL_SCORES_END = 21;

  public ExamInput(String examDirectory)
  {
    try
    {
      FileInput fileInput = new FileInput(examDirectory);
      listOfRowsInData = fileInput.fileInput();
    } catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }

    // All teams for the exams.
    teams = this.teams();
    // Init the school names.
    SCHOOLS = this.getSchools();

    // Inits the lists.
    // First, the exams with no school associated.
    this.exams = this.getExamList();
    // Second, exam with the school, sorted lexicographically with the schools.
    this.examSchools = this.getExamSchoolList();
    // Third, exam with team name.
    this.examTeams = this.getExamTeamList();
  }

  private String[] getSchools()
  {
    HashSet<String> schools = new HashSet<>();

    for (String[] row : listOfRowsInData)
    {
      if (!row[0].equals("Skola"))
      {
        schools.add(row[0]);
      }
    }

    return schools.toArray(new String[0]);
  }

  public int getTotalScore(String school)
  {
    int tot = 0;
    for (ExamSchool examSchool : this.examSchools)
    {
      if (examSchool.getSchool().trim().toLowerCase().equals(school.trim().toLowerCase()))
      {
        int[] sepScores = examSchool.getSeparateScoresForAllQuestions();
        for (int i = 0; i < INDIVIDUAL_SCORES_END - INDIVIDUAL_SCORES_START; i++)
        {
          tot += sepScores[i];
        }
      }
    }

    System.out.println(tot);

    return tot;
  }

  private String[] teams()
  {
    HashSet<String> stringHashSet = new HashSet<>();
    for (String[] s : listOfRowsInData)
    {
      if (!s[0].equals("Skola")) stringHashSet.add(s[3]);
    }

    return stringHashSet.toArray(new String[0]);
  }

  // Helper function that sets the array examSchools to all the Exams + the school name.
  private ExamSchool[] setExamSchoolList()
  {
    int sizeOfExamSchoolArray = 0;

    for (int i = 1; i < listOfRowsInData.size(); i++)
    {
      sizeOfExamSchoolArray++;
    }

    // P2
    ExamSchool[] schools = new ExamSchool[sizeOfExamSchoolArray];

    for (int i = 0; i < schools.length; i++)
    {
      int index = i + 1;
      schools[i] = new ExamSchool(listOfRowsInData.get(index)[0], this.exams[i]);
    }

    return schools;
  }

  // Main function, sorts the examSchoolsarray lexicographically,
  // and inits the examSchoolsarray..
  private ExamSchool[] getExamSchoolList()
  {
    this.examSchools = setExamSchoolList();
    int length = examSchools.length;

    for (int i = 0; i < length; i++)
    {
      for (int j = 1; j < length - i; j++)
      {
        // Compare
        boolean swap = examSchools[j].getSchool().compareTo(examSchools[j - 1].getSchool()) < 0;

        if (swap)
        {
          ExamSchool temp = examSchools[j - 1];
          examSchools[j - 1] = examSchools[j];
          examSchools[j] = temp;
        }
      }
    }

    return examSchools;
  }

  // Helper function to find an Exam in this.exams with the anon code.
  private int findExamByCode(int code)
  {
    for (int i = 0; i < this.exams.length; i++)
    {
      if (exams[i].getAnonymousCode() == code)
      {
        return i;
      }
    }
    return -1;
  }

  /*
Retrieve an array like so:
arr[0] = Alla tentor som associeras med BEST.
arr[1] = Alla tentor som associeras med Cefyrekon
arr[n] = Alla tentor som associeras med SCHOOLS[n]
*/
  public List<ExamSchool[]> getExamSchoolArrayBySchool()
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
  public List<ExamTeam[]> getExamTeamArrayByTeams()
  {
    List<ExamTeam[]> list = new ArrayList<>();

    for (String i : this.teams)
    {
      ExamTeam[] loopArray = this.getExamTeamsByTeam(i);
      list.add(loopArray);
    }

    return list;
  }

  // Helper function to get the ExamTeams[] with all exams associated with input code.
  private ExamTeam[] getExamTeamsByTeam(String team)
  {
    int size = 0;
    for (ExamTeam examTeam : this.examTeams)
    {
      if (examTeam.getTeam().equals(team))
      {
        size++;
      }
    }

    ExamTeam[] teams = new ExamTeam[size];

    int k = 0;
    for (ExamTeam e : this.examTeams)
    {
      if (e.getTeam().equals(team))
      {
        teams[k++] = new ExamTeam(e.getExam(), team);
      }
    }
    return teams;
  }

  private ExamTeam[] getExamTeamList()
  {
    ExamTeam[] examTeams;

    // P1: Init an array with exactly the size of exams[] where the team is the same.
    int sizeOfExamTeamSchool = 0;
    for (int i = 1; i < listOfRowsInData.size(); i++)
    {
      sizeOfExamTeamSchool++;
    }

    examTeams = new ExamTeam[sizeOfExamTeamSchool];
    // P2: Return the array with all those exams with teams in common.

    for (int i = 0; i < examTeams.length; i++)
    {
      examTeams[i] = new ExamTeam(this.exams[i], (listOfRowsInData.get(i + 1)[3]));
    }

    return examTeams;
  }

  private Exam[] getExamList()
  {
    // Format the data.
    Exam[] exams = new Exam[listOfRowsInData.size() - 1];

    for (int i = 1; i < listOfRowsInData.size(); i++)
    {
      int score = Integer.parseInt(listOfRowsInData.get(i)[4]);
      int[] scores = getIndexedSetsFromString(listOfRowsInData.get(i));

      Date date = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      try
      {
        date = format.parse(listOfRowsInData.get(i)[1]);
      } catch (ParseException e)
      {
        e.printStackTrace();
      }

      int code = Integer.parseInt(listOfRowsInData.get(i)[2]);

      // TODO: change when Jonas Kågströms sends all info.
      exams[i - 1] = insertExam(score, scores, date, code);
    }

    return exams;
  }

  private Exam insertExam(int score, int[] scores, Date date, int anonymousCode)
  {
    return new Exam(score,
        scores, date, anonymousCode);
  }

  // Gets the individual scores
  private int[] getIndexedSetsFromString(String[] input)
  {
    int[] list = new int[ExamInput.INDIVIDUAL_SCORES_END - ExamInput.INDIVIDUAL_SCORES_START + 1];

    for (int i = ExamInput.INDIVIDUAL_SCORES_START; i <= ExamInput.INDIVIDUAL_SCORES_END; i++)
    {
      list[i - ExamInput.INDIVIDUAL_SCORES_START] = Integer.parseInt(input[i]);
    }

    return list;
  }

  public Exam[] getExams()
  {
    return this.exams;
  }

  public ExamTeam[] getExamTeams()
  {
    return this.examTeams;
  }

  public ExamSchool[] getExamSchools()
  {
    return examSchools;
  }

  public int[] getExamCodes()
  {
    int[] codes;
    int size = 0;
    for (int i = 0; i < this.exams.length; i++)
    {
      size++;
    }

    codes = new int[size];

    for (int i = 0; i < codes.length; i++)
    {
      codes[i] = exams[i].getAnonymousCode();
    }
    return codes;
  }
}
