package com.bth.exammain;

import com.bth.gui.MainGui;
import java.io.FileNotFoundException;

public class ExamMain {

  public static void main(final String... args) {
    java.awt.EventQueue.invokeLater(() -> {
      try {
        new MainGui();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    });
  }
}
