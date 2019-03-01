package main;

import analysis.ExamAnalysis;

public class ExamMain
{
  public static void main(String... args)
  {
    ExamAnalysis examAnalysis = new ExamAnalysis("/Users/edwincarlsson/Library/Mobile Documents/com~apple~CloudDocs/Java-programmering/src/data/Deltävlingstentamen.csv");
    examAnalysis.start();
  }
}
