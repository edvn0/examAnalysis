package com.bth.exams;

import java.util.Objects;

// Kommer representera poäng i varje skola.
public class ExamSchool extends Exam {

  private final Exam exam;
  private String school;

  public ExamSchool(String school, Exam exam) {
    super(exam.getScore(), exam.getSeparateScoresForAllQuestions(), exam.getDate(),
        exam.getAnonymousCode());
    this.school = school;
    this.exam = exam;
  }

  @Override
  public String toString() {
    return "ExamSchool{" +
        "school = " + this.school +
        ", exam = " + exam.toString() +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ExamSchool)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ExamSchool that = (ExamSchool) o;
    return Objects.equals(getExam(), that.getExam()) &&
        Objects.equals(getSchool(), that.getSchool());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getExam(), getSchool());
  }

  public String getSchool() {
    return school;
  }

  public Exam getExam() {
    return exam;
  }

}
