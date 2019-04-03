package com.bth.gui.csvchooser;

import com.bth.io.output.ExamOutput;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CsvDirectoryChoice {

  public JFileChooser fileChooser1;
  public JPanel primaryPanel;

  public CsvDirectoryChoice() {
    init();
  }

  public void init() {

    fileChooser1.setVisible(true);
    fileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fileChooser1.setDialogTitle("In which directory should CSV be outputted?");

    int returnVal = fileChooser1.showSaveDialog(primaryPanel);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      if (fileChooser1.getSelectedFile().isDirectory()) {
        ExamOutput.setDirectory(fileChooser1.getSelectedFile().getAbsolutePath());
      } else {
        JOptionPane.showMessageDialog(null, "Incorrect input.");
      }
    }
  }
}
