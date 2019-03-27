package com.bth.exams;

import java.util.Objects;

// Kommer att representera poäng för varje lag.
public class ExamTeam extends Exam {

  private Exam exam;
  private String team;

  public ExamTeam(Exam exam, String team) {
    super(exam.getScore(), exam.getSeparateScoresForAllQuestions(), exam.getDate(),
        exam.getAnonymousCode());
    this.exam = exam;
    this.team = team;
  }

  @Override
  public String toString() {
    return "ExamTeam{" +
        "exam=" + exam.toString() +
        ", team=" + team +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ExamTeam)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ExamTeam examTeam = (ExamTeam) o;
    return Objects.equals(getExam(), examTeam.getExam()) &&
        Objects.equals(getTeam(), examTeam.getTeam());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getExam(), getTeam());
  }

  public Exam getExam() {
    return exam;
  }

  public String getTeam() {
    return team;
  }

}
