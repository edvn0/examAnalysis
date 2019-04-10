package com.bth.gui.csvchooser;

import com.bth.io.output.ExamOutput;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CsvDirectoryChoice extends JPanel {

  public JPanel primaryPanel;

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  // Generated using JFormDesigner Evaluation license - Edwin Carlsson
  public JFileChooser fileChooser1;

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


  public CsvDirectoryChoice() {
    initComponents();
    init();
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - Edwin Carlsson
    fileChooser1 = new JFileChooser();

    //======== this ========
    setInheritsPopupMenu(false);
    setMaximumSize(new Dimension(650, 600));
    setMinimumSize(new Dimension(650, 600));
    setPreferredSize(new Dimension(650, 600));

    // JFormDesigner evaluation mark
    setBorder(new javax.swing.border.CompoundBorder(
      new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
        "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
        javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
        java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

    setLayout(new GridLayoutManager(1, 2, new Insets(5, 5, 5, 5), -1, -1));

    //---- fileChooser1 ----
    fileChooser1.setAcceptAllFileFilterUsed(true);
    fileChooser1.setDoubleBuffered(true);
    add(fileChooser1, new GridConstraints(0, 0, 1, 1,
      GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
      GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
      GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
      null, null, null));
    // JFormDesigner - End of component initialization  //GEN-END:initComponents
  }
  // JFormDesigner - End of variables declaration  //GEN-END:variables
}
