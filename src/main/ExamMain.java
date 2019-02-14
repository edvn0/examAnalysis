package main;

import Exams.ExamSchool;
import analysis.ExamAnalysis;

public class ExamMain
{
  public static void main(String... args)
  {
    ExamAnalysis examAnalysis = new ExamAnalysis("/Users/edwincarlsson/Library/Mobile Documents/com~apple~CloudDocs/Java-programmering/src/data/Deltävlingstentamen.csv");
    for (ExamSchool[] examSchools : examAnalysis.getExamSchoolList())
    {
      for (ExamSchool examSchool : examSchools)
      {
        System.out.println(examSchool.toString());
      }
    }
  }
}
