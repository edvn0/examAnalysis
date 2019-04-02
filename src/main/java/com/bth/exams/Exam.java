package com.bth.exams;

import com.mongodb.lang.NonNull;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Exam)) {
      return false;
    }
    Exam exam = (Exam) o;
    return getAnonymousCode() == exam.getAnonymousCode() &&
        Double.compare(exam.getScore(), getScore()) == 0 &&
        getDate().equals(exam.getDate()) &&
        Arrays
            .equals(getSeparateScoresForAllQuestions(), exam.getSeparateScoresForAllQuestions());
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(getAnonymousCode(), getScore(), getDate());
    result = 31 * result + Arrays.hashCode(getSeparateScoresForAllQuestions());
    return result;
  }

  // Mutators!

  public double getScore() {
    return score;
  }

  Date getDate() {
    return date;
  }

  @NonNull
  public double[] getSeparateScoresForAllQuestions() {
    return separateScoresForAllQuestions;
  }

  public int getAnonymousCode() {
    return anonymousCode;
  }

}
