package com.bth.exams;

// Kommer representera poäng i varje skola.
public class ExamSchool extends Exam {
  private final Exam exam;
  private String school;

  public ExamSchool(String school, Exam exam) {
    super(exam.getScore(), exam.getSeparateScoresForAllQuestions(), exam.getDate(), exam.getAnonymousCode());
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


  public String getSchool() {
    return school;
  }

  public Exam getExam() {
    return exam;
  }

}
