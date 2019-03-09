package main;

import analysis.ExamAnalysis;
import gui.MainGUI;

import javax.swing.*;

public class ExamMain
{
  public static void main(String... args)
  {
    ExamAnalysis examAnalysis = new ExamAnalysis("/Users/edwincarlsson/Documents/Programmering/Java-programmering/src/data/Deltävlingstentamen-2.csv");
    examAnalysis.start();

    MainGUI gui = new MainGUI(examAnalysis.getExamSchools(), examAnalysis.getQuestionsStats(), examAnalysis.getStatsSchools(), examAnalysis.getStatsTeams());
    JFrame frame = new JFrame("MainGUI");
    frame.setContentPane(gui.panel1);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
  }
}
