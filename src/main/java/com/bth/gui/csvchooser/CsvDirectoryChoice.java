package com.bth.gui.csvchooser;

import com.bth.io.ExamOutput;
import java.awt.Component;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CsvDirectoryChoice {

  public JFileChooser fileChooser1;
  public JButton exitButton;
  public JButton confirmButton;
  public JPanel primaryPanel;
  public JPanel labelPanel;
  public JPanel filePanel;
  public JLabel dirLabel;
  private JFrame frame;

  private File directory;

  public CsvDirectoryChoice() {
    directory = null;

    frame = new JFrame("In which directory should CSV be outputted?");
    frame.setContentPane(this.primaryPanel);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.pack();
    frame.setVisible(false);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);

    fileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    confirmButton.addActionListener(e -> {
      directory = fileChooser1.getSelectedFile();
      ExamOutput.setDirectory(directory.getAbsolutePath());
      frame.dispose();
    });

    exitButton.addActionListener(e -> frame.dispose());
  }

  public File getDirectory() {
    return directory;
  }

  public Component getFrame() {
    return frame;
  }
}
