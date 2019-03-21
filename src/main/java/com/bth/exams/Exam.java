package com.bth.exams;

import java.util.Arrays;
import java.util.Date;

// Main object, ska representera tentorna, med vilken kod de skrevs, alla separata poäng, datum.
public class Exam {
  private int anonymousCode;
  private double score;
  private Date date;
  private double[] separateScoresForAllQuestions;

  public Exam(double score, double[] scores, Date date, int anonymousCode) {
    this.anonymousCode = anonymousCode;
    this.separateScoresForAllQuestions = scores;
    this.score = score;
    this.date = date;
  }

  @Override
  public String toString() {
    return "Exam{" +
        "anonymousCode=" + anonymousCode +
        ", score=" + score +
        ", date=" + date +
        ", separateScoresForAllQuestions=" + Arrays.toString(separateScoresForAllQuestions) +
        '}';
  }

  // Mutators!

  public double getScore() {
    return score;
  }

  Date getDate() {
    return date;
  }

  public double[] getSeparateScoresForAllQuestions() {
    return separateScoresForAllQuestions;
  }

  public int getAnonymousCode() {
    return anonymousCode;
  }

}
