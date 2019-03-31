package com.bth.gui.examdirectorygui;

import com.bth.gui.MainGUI;
import com.bth.io.PropertiesReader;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ChooseInputFileFrame {

  public JPanel mainPanel;
  public JLabel chooseLabel;
  public JFileChooser fileChooser1;

  public ChooseInputFileFrame() {
    // DEV!
    try {
      fileChooser1.setCurrentDirectory(new File(new PropertiesReader(
          "/Users/edwincarlsson/Documents/Programmering/exam_Analysis/.properties")
          .getProperty("file.chooser.directory")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    // END DEV

    fileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);

    fileChooser1.setDialogTitle("Please choose csv file for input");
    int retVal = fileChooser1.showSaveDialog(null);
    if (retVal == JFileChooser.APPROVE_OPTION) {
      if (!fileChooser1.getSelectedFile().isDirectory()) {
        MainGUI.setFileFromChooseDirectory(fileChooser1.getSelectedFile());
      } else {
        JOptionPane.showMessageDialog(null, "Incorrect input.");
      }
    }

  }
}
