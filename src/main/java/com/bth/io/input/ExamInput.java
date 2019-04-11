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

import com.bth.analysis.stats.StatsSchool;
import com.bth.exams.Exam;
import com.bth.exams.ExamSchool;
import com.bth.exams.ExamTeam;
import com.bth.gui.controller.loginusers.SQLLoginUser;
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
  private ExamSchool[] examSchools;

  private static int[] amountOfSameSchools;
  private int INDIVIDUAL_SCORES_START;
  private int INDIVIDUAL_SCORES_END;
  private final String[] teams;
  public String[] schools;

  private List<String[]> listOfRowsInData;
  private List<String[]> listFromDatabase;

  /***
   * Starts the whole process, inits all the information of the arrays from
   * a FileInput object. Has three "main" objects associated with it:
   * exams, examTeams, examSchools which are extracted from this object.
   * @param examDirectory where the csv input file is located.
   */
  public ExamInput(String examDirectory) throws FileNotFoundException {

    FileInput fileInput = new FileInput(examDirectory);
    listOfRowsInData = fileInput.fileInput();

    INDIVIDUAL_SCORES_START = fileInput
        .getIndex(true, listOfRowsInData, "Fråga 1 Poäng", "Fråga 14 Poäng") != -1 ?
        fileInput.getIndex(true, listOfRowsInData, "Fråga 1 Poäng", "Fråga 14 Poäng") : 0;

    INDIVIDUAL_SCORES_END = fileInput
        .getIndex(false, listOfRowsInData, "Fråga 1 Poäng", "Fråga 14 Poäng") != -1 ?
        fileInput.getIndex(false, listOfRowsInData, "Fråga 1 Poäng", "Fråga 14 Poäng") : 0;

    if (INDIVIDUAL_SCORES_END == 0 && INDIVIDUAL_SCORES_START == 0) {
      JOptionPane.showMessageDialog(null, "Could not find the file, try again.", "Error",
          JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }

    // All getTeams for the java.exams.
    teams = this.getTeams(listOfRowsInData);
    // Init the school names.
    schools = this.getSchools(listOfRowsInData);

    // Inits the lists.
    // First, the java.exams with no school associated.
    this.exams = this.getExamList(listOfRowsInData);
    // Second, exam with the school, sorted lexicographically with the schools.
    this.examSchools = this.getExamSchoolList(listOfRowsInData);
    amountOfSameSchools = this.getAmountOfExamsFromSameSchool(this.examSchools, schools);
    // Third, exam with team name.
    this.examTeams = this.getExamTeamList(listOfRowsInData);
  }

  public ExamInput(SQLLoginUser user) {
    FileInput fileInput = new FileInput(user);
    listFromDatabase = fileInput.fileInputFromDatabase();

    INDIVIDUAL_SCORES_START = fileInput
        .getMetaDataQuestionIndex(fileInput.getResultSet(), "q1", "q14", true);
    INDIVIDUAL_SCORES_END = fileInput
        .getMetaDataQuestionIndex(fileInput.getResultSet(), "q1", "q14", false);

    this.teams = this.getTeams(listFromDatabase);
    schools = this.getSchools(listFromDatabase);

    this.exams = this.getExamList(listFromDatabase);
    this.examSchools = this.getExamSchoolList(listFromDatabase);
    amountOfSameSchools = this.getAmountOfExamsFromSameSchool(this.examSchools, schools);
    this.examTeams = this.getExamTeamList(listFromDatabase);
  }

  /***
   * Returns the max score of a question, stored in an enum.
   * @param question what question?
   * @return questions associated max score.
   */
  public int getMaxScore(String question) {
    for (ScoreEnum scores : ScoreEnum.values()) {
      if (Integer.parseInt(question) == scores.index) {
        return scores.max;
      }
    }
    return -1;
  }

  public int[] getAmountOfExamsFromSameSchool(ExamSchool[] statsSchools, String[] schools) {
    // TODO: this might be the bug for TODO in ExamOutput. Test!
    int[] j = new int[schools.length];
    for (ExamSchool statsSchool : statsSchools) {
      for (int k = 0; k < schools.length; k++) {
        if (statsSchool.getSchool().toLowerCase().trim().equals(schools[k].toLowerCase().trim())) {
          j[k]++;
        }
      }
    }

    return j;
  }

  public int getSchoolIndex(StatsSchool statsSchool) {
    for (int i = 0; i < this.schools.length; i++) {
      String school = this.schools[i];
      if (statsSchool.getSchool().equals(school)) {
        return amountOfSameSchools[i];
      }
    }
    return -1;
  }

  private String[] getTeams(List<String[]> data) {
    HashSet<String> stringHashSet = new HashSet<>();
    for (String[] s : data) {
      stringHashSet.add(s[3]);
    }

    return stringHashSet.toArray(new String[0]);
  }

  private int isFirstRowTimeStamp(List<String[]> data) {
    return data.get(0)[0].equals("Tidstämpel") ? 1 : 0;
  }

  private String[] getSchools(List<String[]> data) {
    HashSet<String> schools = new HashSet<>();

    for (String[] row : data) {
      schools.add(row[1]);
    }

    String[] retArr = new String[schools.size()];
    return schools.toArray(retArr);
  }

  /***
   * Start-point for the exam data structure.
   * @return a list of all exams in the csv file.
   * @param listData input data
   */
  private Exam[] getExamList(List<String[]> listData) {
    Exam[] exams = new Exam[listData.size()];
    for (int i = 0; i < exams.length; i++) {
      String[] row = listData.get(i);

      double[] scores = getScoresFromRowData(row);
      double score = Double.parseDouble(row[INDIVIDUAL_SCORES_END]);

      SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-mm-dd");
      Date date = null;
      try {
        date = dateFormat.parse(row[3]);
      } catch (ParseException e) {
        e.printStackTrace();
      }

      int anonCode = Objects.hash(score, scores, date);

      exams[i] = new Exam(score, scores, date, anonCode);
    }
    return exams;
  }

  // Main function, sorts the examSchoolsarray lexicographically,
  // and inits the examSchoolsarray..
  private ExamSchool[] getExamSchoolList(List<String[]> list) {
    this.examSchools = setExamSchoolList(list);

    Arrays.sort(examSchools, Comparator.comparing(ExamSchool::getSchool));

    return examSchools;
  }

  private ExamTeam[] getExamTeamList(List<String[]> list) {
    ExamTeam[] examTeams;
    int sizeOfExamTeamSchool = 0;
    for (int i = 0; i < list.size(); i++) {
      sizeOfExamTeamSchool++;
    }
    examTeams = new ExamTeam[sizeOfExamTeamSchool];
    for (int i = 0; i < examTeams.length; i++) {
      examTeams[i] = new ExamTeam(this.exams[i],
          list.get(i)[2]);
    }
    return examTeams;
  }

  // Gets the individual scores from the FileInput CSV file.
  //
  private double[] getScoresFromRowData(String[] input) {
    int end = (this.INDIVIDUAL_SCORES_END);
    int start = (this.INDIVIDUAL_SCORES_START - 1);
    int listSize = (end - start);
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
  private ExamSchool[] setExamSchoolList(List<String[]> data) {
    int sizeOfExamSchoolArray = 0;
    for (int i = 1; i < data.size(); i++) {
      sizeOfExamSchoolArray++;
    }

    ExamSchool[] schools = new ExamSchool[sizeOfExamSchoolArray];
    for (int i = 0; i < schools.length; i++) {
      String schoolName = data.get(i)[1];

      schools[i] = new ExamSchool(schoolName, this.exams[i]);
    }

    return schools;
  }

  public ExamTeam[] getExamTeams() {
    return this.examTeams;
  }

  public ExamSchool[] getExamSchools() {
    return examSchools;
  }


}
