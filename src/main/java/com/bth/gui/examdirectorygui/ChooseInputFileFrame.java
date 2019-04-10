package com.bth.gui.examdirectorygui;

import com.bth.gui.MainGUI.MainGui;
import com.bth.io.input.PropertiesReader;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ChooseInputFileFrame extends JPanel {

  public JPanel mainPanel;

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  // Generated using JFormDesigner Evaluation license - Edwin Carlsson
  public JFileChooser fileChooser1;
  public JLabel chooseLabel;

  public ChooseInputFileFrame() {
    // DEV!
    initComponents();
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
        MainGui.setFileFromChooseDirectory(fileChooser1.getSelectedFile());
      } else {
        JOptionPane.showMessageDialog(null, "Incorrect input.");
      }
    }

  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - Edwin Carlsson
    fileChooser1 = new JFileChooser();
    chooseLabel = new JLabel();

    //======== this ========
    setBackground(new Color(224, 224, 223));
    setForeground(new Color(180, 29, 141));
    setMaximumSize(new Dimension(730, 490));
    setMinimumSize(new Dimension(730, 490));
    setOpaque(true);
    setPreferredSize(new Dimension(730, 490));
    setBorder(new TitledBorder(new LineBorder(new Color(180, 29, 141)), ""));

    // JFormDesigner evaluation mark
    setBorder(new javax.swing.border.CompoundBorder(
      new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
        "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
        javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
        java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

    setLayout(new GridLayoutManager(2, 1, new Insets(15, 15, 15, 15), -1, -1));

    //---- fileChooser1 ----
    fileChooser1.setDoubleBuffered(true);
    add(fileChooser1, new GridConstraints(1, 0, 1, 1,
      GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
      GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
      GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
      null, null, null));

    //---- chooseLabel ----
    chooseLabel.setBackground(new Color(224, 224, 223));
    chooseLabel.setFont(new Font("Monaco", Font.BOLD, 22));
    chooseLabel.setForeground(new Color(180, 29, 141));
    chooseLabel.setText("Choose the file from which the analysis will be done");
    add(chooseLabel, new GridConstraints(0, 0, 1, 1,
      GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
      GridConstraints.SIZEPOLICY_FIXED,
      GridConstraints.SIZEPOLICY_FIXED,
      null, null, null));
    // JFormDesigner - End of component initialization  //GEN-END:initComponents
  }
  // JFormDesigner - End of variables declaration  //GEN-END:variables
}
