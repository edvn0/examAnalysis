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

    //ExamOutput.insertIntoDatabase(examAnalysis.getStatsTeams(), null);
    //ExamOutput.insertIntoDatabase(null, examAnalysis.getStatsSchools());
    ExamOutput.insertIntoDatabase(null, null);
  }
}
