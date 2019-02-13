package Exams;

// Kommer representera poäng i varje skola.
public class ExamSchool extends Exam
{
  private Exam exam;
  private String school;

  public ExamSchool(String school, Exam exam)
  {
    super(exam.getScore(), exam.getSeparateScoresForAllQuestions(), exam.getDate(), exam.getAnonymousCode());
    this.school = school;
    this.exam = exam;
  }

  @Override
  public String toString()
  {
    return "ExamSchool{" +
        "school=" + school.toUpperCase() +
        ", exam='" + exam.toString() + '\'' +
        '}';
  }

  public int getScore()
  {
    return exam.getScore();
  }

  public String getSchool()
  {
    return school;
  }

  public Exam getExam()
  {
    return exam;
  }

  public void setSchool(String school)
  {
    this.school = school;
  }
}
