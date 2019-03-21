package com.bth.exammain;

import com.bth.analysis.ExamAnalysis;
import com.bth.gui.MainGUI;
import com.bth.gui.examdirectorygui.ChooseDirectory;

import javax.swing.*;

public class ExamMain {
  public static String dir =
      "/Users/edwincarlsson/Documents/Programmering/Java-programmering/src/main/resources/data/csvfiles/Deltävlingstentamen_2019_03_21.csv";

  public static void main(final String... args) {
    java.awt.EventQueue.invokeLater(() -> {
      ChooseDirectory chooseDirectory = new ChooseDirectory();
    });

    java.awt.EventQueue.invokeLater(() -> {
      ExamAnalysis examAnalysis = new ExamAnalysis(dir);
      examAnalysis.start();
      MainGUI gui = new MainGUI(examAnalysis.getExamSchools(), examAnalysis.getQuestionsStats(), examAnalysis.getStatsSchools(), examAnalysis.getStatsTeams());
      JFrame frame = new JFrame("MainGUI");
      frame.setContentPane(gui.panel1);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setResizable(false);
      frame.setVisible(true);
      frame.setLocationRelativeTo(null);
    });
  }

  public static void setDir(String dir) {
    ExamMain.dir = dir;
  }
}
