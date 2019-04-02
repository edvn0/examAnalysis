package com.bth.exammain;

import com.bth.gui.MainGUI;
import java.io.FileNotFoundException;

public class ExamMain {

  public static void main(final String... args) {
    java.awt.EventQueue.invokeLater(() -> {
      try {
        new MainGUI();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    });
  }
}
