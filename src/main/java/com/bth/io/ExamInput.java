package com.bth.io;

import com.bth.exams.Exam;
import com.bth.exams.ExamSchool;
import com.bth.exams.ExamTeam;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class ExamInput {

  private final Exam[] exams;
  private final ExamTeam[] examTeams;
  private final String[] teams;
  private ExamSchool[] examSchools;
  private List<String[]> listOfRowsInData;
  private int INDIVIDUAL_SCORES_START;
  private int INDIVIDUAL_SCORES_END;

  /***
   * Starts the whole process, inits all the information of the arrays from
   * a FileInput object. Has three "main" objects associated with it:
   * exams, examTeams, examSchools which are extracted from this object.
   * @param examDirectory where the csv input file is located.
   */
  public ExamInput(String examDirectory) {

    // Inits where the scores are in the tsv file, necessary for future use.
    try {
      FileInput fileInput = new FileInput(examDirectory);
      listOfRowsInData = fileInput.fileInput();

      INDIVIDUAL_SCORES_START = fileInput
          .getIndex(true, listOfRowsInData) != -1 ?
          fileInput.getIndex(true, listOfRowsInData) : 0;

      INDIVIDUAL_SCORES_END = fileInput
          .getIndex(false, listOfRowsInData) != -1 ?
          fileInput.getIndex(false, listOfRowsInData) : 0;

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if (INDIVIDUAL_SCORES_END == 0 && INDIVIDUAL_SCORES_START == 0) {
      System.exit(0);
    }

    // All teams for the java.exams.
    teams = this.teams(this.isFirstRowTimeStamp());
    // Init the school names.
    String[] SCHOOLS = this.getSchools(this.isFirstRowTimeStamp());

    // Inits the lists.
    // First, the java.exams with no school associated.
    this.exams = this.getExamList();
    // Second, exam with the school, sorted lexicographically with the schools.
    this.examSchools = this.getExamSchoolList();
    // Third, exam with team name.
    this.examTeams = this.getExamTeamList();
  }

  private String[] teams(int isTimeStampDependent) {
    HashSet<String> stringHashSet = new HashSet<>();
    for (String[] s : listOfRowsInData) {
      if (!s[isTimeStampDependent].equals("Skola")) {
        stringHashSet.add(s[3]);
      }
    }

    return stringHashSet.toArray(new String[0]);
  }

  private int isFirstRowTimeStamp() {
    return listOfRowsInData.get(0)[0].equals("Tidstämpel") ? 1 : 0;
  }

  private String[] getSchools(int isTimeStampDependent) {
    HashSet<String> schools = new HashSet<>();

    for (String[] row : listOfRowsInData) {
      if (!row[isTimeStampDependent].equals("Skola")) {
        schools.add(row[isTimeStampDependent]);
      }
    }

    return schools.toArray(new String[0]);
  }

  private Exam[] getExamList() {
    // Format the java.data.
    Exam[] exams = new Exam[listOfRowsInData.size() - 1];
    int timeStampDependentIndex = isFirstRowTimeStamp();

    for (int i = 1; i < listOfRowsInData.size(); i++) {
      double score = Double.parseDouble(listOfRowsInData.get(i)[3 + timeStampDependentIndex]);
      double[] scores = getScoresFromRowData(listOfRowsInData.get(i));

      Date date = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      try {
        date = format.parse(listOfRowsInData.get(i)[1 + timeStampDependentIndex]);
      } catch (ParseException e) {
        e.printStackTrace();
      }

      int code = Objects.hash(listOfRowsInData.get(i)[2 + timeStampDependentIndex]);

      exams[i - 1] = insertExam(score, scores, date, code);
    }

    return exams;
  }

  // Main function, sorts the examSchoolsarray lexicographically,
  // and inits the examSchoolsarray..
  private ExamSchool[] getExamSchoolList() {
    this.examSchools = setExamSchoolList(this.isFirstRowTimeStamp());

    Arrays.sort(examSchools, Comparator.comparing(ExamSchool::getSchool));

    return examSchools;
  }

  private ExamTeam[] getExamTeamList() {
    ExamTeam[] examTeams;
    int isTimeStampDependent = this.isFirstRowTimeStamp();

    // P1: Init an array with exactly the size of java.exams[] where the team is the same.
    int sizeOfExamTeamSchool = 0;
    for (int i = 1; i < listOfRowsInData.size(); i++) {
      sizeOfExamTeamSchool++;
    }

    examTeams = new ExamTeam[sizeOfExamTeamSchool];
    // P2: Return the array with all those java.exams with teams in common.

    for (int i = 0; i < examTeams.length; i++) {
      int index = i + 1;
      examTeams[index - 1] = new ExamTeam(this.exams[index - 1],
          (listOfRowsInData.get(index)[2 + isTimeStampDependent]));
    }

    return examTeams;
  }

  // Gets the individual scores
  private double[] getScoresFromRowData(String[] input) {
    int end = (this.INDIVIDUAL_SCORES_END);
    int start = (this.INDIVIDUAL_SCORES_START);
    int listSize = end - start;
    double[] list = new double[listSize];

    for (int i = start; i < end; i++) {
      list[i - start] = Double.parseDouble(input[i]);
    }

    return list;
  }

  private Exam insertExam(double score, double[] scores, Date date, int anonymousCode) {
    return new Exam(score,
        scores, date, anonymousCode);
  }

  // Helper function that sets the array examSchools to all the java.exams + the school name.
  private ExamSchool[] setExamSchoolList(int isTimeStampDependent) {
    int sizeOfExamSchoolArray = 0;

    for (int i = 1; i < listOfRowsInData.size(); i++) {
      sizeOfExamSchoolArray++;
    }

    // P2
    ExamSchool[] schools = new ExamSchool[sizeOfExamSchoolArray];

    for (int i = 0; i < schools.length; i++) {
      int index = i + 1;
      schools[i] = new ExamSchool(listOfRowsInData.get(index)[isTimeStampDependent], this.exams[i]);
    }

    return schools;
  }

  /***
   * Returns the max score of a question, stored in an enum.
   * @param question what question?
   * @return questions associated max score.
   */
  public static int getMaxScore(String question) {
    for (Scores scores : Scores.values()) {
      if (Integer.parseInt(question) == scores.index) {
        return scores.max;
      }
    }
    return -1;
  }

  // Helper function to get the ExamSchool[] with all java.exams associated with input school.
  private ExamSchool[] getExamSchoolsBySchool(String school) {
    int size = 0;
    for (ExamSchool examSchool : this.examSchools) {
      if (examSchool.getSchool().toLowerCase().trim().equals(school.toLowerCase().trim())) {
        size++;
      }
    }

    ExamSchool[] examSchools = new ExamSchool[size];

    int k = 0;
    for (ExamSchool examSchool : this.examSchools) {
      if (examSchool.getSchool().toLowerCase().trim().equals(school.toLowerCase().trim())) {
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
  public List<ExamTeam[]> getExamTeamArrayByTeams() {
    List<ExamTeam[]> list = new ArrayList<>();

    for (String i : this.teams) {
      ExamTeam[] loopArray = this.getExamTeamsByTeam(i);
      list.add(loopArray);
    }

    return list;
  }

  // Helper function to get the ExamTeams[] with all java.exams associated with input code.
  private ExamTeam[] getExamTeamsByTeam(String team) {
    int size = 0;
    for (ExamTeam examTeam : this.examTeams) {
      if (examTeam.getTeam().equals(team)) {
        size++;
      }
    }

    ExamTeam[] teams = new ExamTeam[size];

    int k = 0;
    for (ExamTeam e : this.examTeams) {
      if (e.getTeam().equals(team)) {
        teams[k++] = new ExamTeam(e.getExam(), team);
      }
    }
    return teams;
  }

  public ExamTeam[] getExamTeams() {
    return this.examTeams;
  }

  public ExamSchool[] getExamSchools() {
    return examSchools;
  }

  private enum Scores {
    qOne(10, 1),
    qTwo(2, 2),
    qThree(4, 3),
    qFour(4, 4),
    qFive(10, 5),
    qSix(10, 6),
    qSeven(5, 7),
    qEight(5, 8),
    qNine(5, 9),
    qTen(5, 10),
    qEleven(5, 11),
    qTwelve(5, 12),
    qThirteen(5, 13),
    qFourteen(5, 14);

    private final int max;
    private final int index;

    Scores(int max, int index) {
      this.max = max;
      this.index = index;
    }
  }
}
