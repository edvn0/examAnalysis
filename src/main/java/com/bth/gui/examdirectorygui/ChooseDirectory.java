package com.bth.gui.examdirectorygui;

import com.bth.gui.MainGUI;
import java.io.File;
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

    chosenFile = null;
    confirmButton.addActionListener((e) -> {
      chosenFile = fileChooser1.getSelectedFile();
      MainGUI.setFileFromChooseDirectory(chosenFile);
      frame.dispose();
    });
  }

  public File getChosenFile() {
    return chosenFile;
  }

  public JButton getConfirmButton() {
    return confirmButton;
  }
}
