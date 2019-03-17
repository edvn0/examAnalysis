package javastructure.main;

import javastructure.analysis.ExamAnalysis;
import javastructure.gui.MainGUI;

import javax.swing.*;

public class ExamMain
{
  public static void main(final String... args)
  {
    ExamAnalysis examAnalysis = new ExamAnalysis(
        "/Users/edwincarlsson/Documents/Programmering" +
            "/Java-programmering/src/javastructure/data/Deltävlingstentamen.csv");

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
