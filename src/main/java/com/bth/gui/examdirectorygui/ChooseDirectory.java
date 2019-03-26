package com.bth.gui.examdirectorygui;

import com.bth.gui.MainGUI;
import com.bth.io.PropertiesReader;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChooseDirectory {

  public JPanel mainPanel;
  public JLabel chooseLabel;
  public JFileChooser fileChooser1;
  public JButton confirmButton;

  private File chosenFile;

  public ChooseDirectory() {
    JFrame frame = new JFrame("CSV file for Exam Analysis");
    frame.setContentPane(this.mainPanel);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // DEV!
    try {
      fileChooser1.setCurrentDirectory(new File(new PropertiesReader(
          "/Users/edwincarlsson/Documents/Programmering/exam_Analysis/.properties")
          .getProperty("file.chooser.directory")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    // END DEV

    chosenFile = null;
    confirmButton.addActionListener((e) -> {
      System.out.println(confirmButton);
      System.out.println(fileChooser1.getSelectedFile());
      chosenFile = fileChooser1.getSelectedFile();
      MainGUI.setFileFromChooseDirectory(chosenFile);
      frame.dispose();
    });
  }
}
