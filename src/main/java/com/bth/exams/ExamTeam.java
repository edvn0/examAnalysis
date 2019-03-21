package com.bth.exams;

// Kommer att representera poäng för varje lag.
public class ExamTeam extends Exam {
  private Exam exam;
  private String team;

  public ExamTeam(Exam exam, String team) {
    super(exam.getScore(), exam.getSeparateScoresForAllQuestions(), exam.getDate(), exam.getAnonymousCode());
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

  public Exam getExam() {
    return exam;
  }

  public String getTeam() {
    return team;
  }

}
