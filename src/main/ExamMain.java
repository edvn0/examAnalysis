package main;

import analysis.ExamAnalysis;
import io.ExamOutput;

public class ExamMain
{
  public static void main(String... args)
  {
    ExamAnalysis examAnalysis = new ExamAnalysis("/Users/edwincarlsson/Library/Mobile Documents/com~apple~CloudDocs/Java-programmering/src/data/Deltävlingstentamen.csv");
    examAnalysis.start();

    ExamOutput.printToCSV_Teams(examAnalysis.getStatsTeams());
    ExamOutput.printToCSV_Schools(examAnalysis.getStatsSchools());

    System.out.println("First");
    ExamOutput.insertIntoDatabase(examAnalysis.getStatsTeams(), null, null);
    System.out.println("Second");
    ExamOutput.insertIntoDatabase(null, examAnalysis.getStatsSchools(), null);
    System.out.println("Third");
    ExamOutput.insertIntoDatabase(null, null, examAnalysis.getQuestionsStats());
  }
}
