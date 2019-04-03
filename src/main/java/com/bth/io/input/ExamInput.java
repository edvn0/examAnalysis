/*
 * Copyright (c) 2018-2019 Edwin Carlsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.bth.io.input;

import com.bth.exams.Exam;
import com.bth.exams.ExamSchool;
import com.bth.exams.ExamTeam;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;

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
  public ExamInput(String examDirectory) throws FileNotFoundException {

    // Inits where the scores are in the tsv file, necessary for future use.
    FileInput fileInput = new FileInput(examDirectory);
    listOfRowsInData = fileInput.fileInput();

    INDIVIDUAL_SCORES_START = fileInput
        .getIndex(true, listOfRowsInData) != -1 ?
        fileInput.getIndex(true, listOfRowsInData) : 0;

    INDIVIDUAL_SCORES_END = fileInput
        .getIndex(false, listOfRowsInData) != -1 ?
        fileInput.getIndex(false, listOfRowsInData) : 0;

    if (INDIVIDUAL_SCORES_END == 0 && INDIVIDUAL_SCORES_START == 0) {
      JOptionPane.showMessageDialog(null, "Could not find the file, try again.", "Error",
          JOptionPane.ERROR_MESSAGE);
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

    String[] retArr = new String[schools.size()];
    return schools.toArray(retArr);
  }

  /***
   * Start-point for the exam data structure.
   * @return a list of all exams in the csv file.
   */
  private Exam[] getExamList() {
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

      // Inserts the exam at index in the field Exam[].
      insertExam(exams, i - 1, score, scores, date, code);
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
    int sizeOfExamTeamSchool = 0;
    for (int i = 1; i < listOfRowsInData.size(); i++) {
      sizeOfExamTeamSchool++;
    }
    examTeams = new ExamTeam[sizeOfExamTeamSchool];
    for (int i = 0; i < examTeams.length; i++) {
      int index = i + 1;
      examTeams[index - 1] = new ExamTeam(this.exams[index - 1],
          listOfRowsInData.get(index)[2 + isTimeStampDependent]);
    }
    return examTeams;
  }

  // Gets the individual scores from the FileInput CSV file.
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

  private void insertExam(Exam[] exams, int index, double score, double[] scores, Date date,
      int anonymousCode) {
    exams[index] = new Exam(score,
        scores, date, anonymousCode);
  }

  // Helper function that sets the array examSchools to all the java.exams + the school name.
  private ExamSchool[] setExamSchoolList(int isTimeStampDependent) {
    int sizeOfExamSchoolArray = 0;
    for (int i = 1; i < listOfRowsInData.size(); i++) {
      sizeOfExamSchoolArray++;
    }

    ExamSchool[] schools = new ExamSchool[sizeOfExamSchoolArray];
    for (int i = 0; i < schools.length; i++) {
      int index = i + 1;
      schools[i] = new ExamSchool(listOfRowsInData.get(index)[isTimeStampDependent], this.exams[i]);
    }

    return schools;
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
