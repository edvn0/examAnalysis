package com.bth.exammain;

import com.bth.gui.MainGUI.MainGui;
import java.io.FileNotFoundException;

public class ExamMain {

  public static void main(final String... args) {

    /*SQLLoginUser user = new SQLLoginUser("Edwin", "Edwin98".toCharArray(), "test_input",
        "jdbc:mysql://localhost:8889/test-input-data?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC");
    FileInput fileInput = new FileInput(user);

    List<String[]> strings = fileInput.fileInputFromDatabase();*/

    java.awt.EventQueue.invokeLater(() -> {
      try {
        new MainGui();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    });
  }
}
