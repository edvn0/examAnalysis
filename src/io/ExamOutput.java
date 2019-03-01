package io;

import Exams.Exam;
import Exams.ExamSchool;

public class ExamOutput
{
  public static String examsToString(Exam[] exams)
  {
    StringBuilder builder = new StringBuilder();
    for (var i = 0; i < exams.length; i++)
    {
      builder.append("\nExam: ").append(i).append(" ").append("Id: ").append(exams[i].getAnonymousCode()).append(" Score:").append(exams[i].getScore());
    }
    return builder.toString();
  }

  public static String examSchoolsToString(ExamSchool[] exams)
  {
    StringBuilder string = new StringBuilder();
    for (ExamSchool exam : exams)
    {
      string.append("\n");
      string.append("Exam: ").append(exam.getAnonymousCode()).append("\nScore: ").append(exam.getScore());
    }
    return string.toString();
  }
}
