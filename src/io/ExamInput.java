package io;

import Exams.Exam;
import Exams.ExamSchool;
import Exams.ExamTeam;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExamInput
{
  private Exam[] exams;
  private ExamTeam[] examTeams;
  private ExamSchool[] examSchools;

  private List<String[]> listOfRowsInData;

  private static final int INDIVIDUAL_SCORES_START = 4;
  private static final int INDIVIDUAL_SCORES_END = 19;

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

    // Inits the lists.
    // First, the exams with no school associated.
    this.exams = this.getExamList();
    // Second, exam with the school, sorted lexicographically with the schools.
    this.examSchools = this.getExamSchoolList();
    // Third, exam with team name.
    this.examTeams = this.getExamTeamList();
  }

  // This gets the subset array of examSchools with the school equal to input.
  // TODO: fix this! Get schools and teams.
  protected ExamSchool[] getExamSchoolList(String school)
  {
    // P1: Init an array with exactly the size of exams[] where the schools are the same.
    // P2: Return the array with all those exams with school in common.

    // P1
    int sizeOfExamSchoolArray = 0;

    for (int i = 2; i < listOfRowsInData.size(); i++)
    {
      if (listOfRowsInData.get(i)[0].toLowerCase().trim().equals(school.toLowerCase().trim()))
      {
        sizeOfExamSchoolArray++;
      }
    }

    // P2
    ExamSchool[] schools = new ExamSchool[sizeOfExamSchoolArray];
    int insert = 0;

    for (String[] strings : listOfRowsInData)
    {
      String schoolInList = strings[0].toLowerCase().trim();
      String schoolParamater = school.toLowerCase().trim();

      if (schoolInList.equals(schoolParamater) && findExamByCode(Integer.parseInt(strings[2])) != -1)
      {
        int index = this.findExamByCode(Integer.parseInt(strings[2]));
        schools[insert++] = new ExamSchool(school, this.exams[index]);
      }
    }

    return schools;
  }

  // Helper function that sets the array examSchools to all the Exams + the school name.
  private ExamSchool[] setExamSchoolList()
  {
    int sizeOfExamSchoolArray = 0;

    for (int i = 2; i < listOfRowsInData.size(); i++)
    {
      sizeOfExamSchoolArray++;
    }

    // P2
    ExamSchool[] schools = new ExamSchool[sizeOfExamSchoolArray];

    for (int i = 0; i < schools.length; i++)
    {
      int index = i + 2;
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

  private ExamTeam[] getExamTeamList()
  {
    ExamTeam[] examTeams;

    // P1: Init an array with exactly the size of exams[] where the team is the same.
    int sizeOfExamTeamSchool = 0;
    for (int i = 2; i < listOfRowsInData.size(); i++)
    {
      sizeOfExamTeamSchool++;
    }

    examTeams = new ExamTeam[sizeOfExamTeamSchool];
    // P2: Return the array with all those exams with teams in common.

    for (int i = 0; i < examTeams.length; i++)
    {
      examTeams[i] = new ExamTeam(this.exams[i], Integer.parseInt(listOfRowsInData.get(i + 2)[2]));
    }

    return examTeams;
  }

  private Exam[] getExamList()
  {
    // Format the data.
    Exam[] exams = new Exam[listOfRowsInData.size() - 2];

    for (int i = 2; i < listOfRowsInData.size(); i++)
    {
      int score = Integer.parseInt(listOfRowsInData.get(i)[3]);
      int[] scores = getIndexedSetsFromString(listOfRowsInData.get(i)
      );

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
      exams[i - 2] = insertExam(score, scores, date, code);
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
    for (int i = 0; i < exams.length; i++)
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
