package main;

import analysis.ExamAnalysis;

import java.util.Arrays;

public class ExamMain
{
    public static void main(String... args)
    {
        ExamAnalysis examAnalysis = new ExamAnalysis("/Users/edwincarlsson/Library/Mobile Documents/com~apple~CloudDocs/Java-programmering/src/data/example_data.csv");
        System.out.println(Arrays.toString(examAnalysis.getExams()));
        System.out.println(Arrays.toString(examAnalysis.getExamSchools()));
        System.out.println(Arrays.toString(examAnalysis.getExamTeams()));
    }
}
