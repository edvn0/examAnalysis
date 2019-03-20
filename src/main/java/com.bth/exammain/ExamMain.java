package com.bth.exammain;

import com.bth.analysis.ExamAnalysis;
import com.bth.gui.MainGUI;

import javax.swing.*;

public class ExamMain
{
  public static void main(final String... args)
  {
    ExamAnalysis examAnalysis = new ExamAnalysis(
        "/Users/edwincarlsson/Documents/Programmering/Java-programmering/src/main/java/com.bth/data/csvfiles/Deltävlingstentamen_2019_03_20.csv");

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
