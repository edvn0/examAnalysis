/*
 * Copyright (c) 2018-2019 Edwin Carlsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/*
 * Created by JFormDesigner on Wed Apr 10 17:17:33 CEST 2019
 */

package com.bth.gui.dbinput;

import com.bth.gui.controller.loginusers.SQLLoginUser;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author Edwin Carlsson
 */
public class ExamsFromDatabaseInput extends JFrame {

  SQLLoginUser user;
  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  // Generated using JFormDesigner Evaluation license - Edwin Carlsson
  private JPanel dialogPane;
  private JPanel contentPanel;
  private JLabel label1;
  private JLabel label2;
  private JLabel label3;
  private JLabel label5;
  private JLabel label4;
  private JTextField textField1;
  private JPasswordField passwordField1;
  private JTextField textField2;
  private JScrollPane scrollPane1;
  private JTextArea textArea1;
  private JPanel buttonBar;
  private JButton okButton;
  private JButton cancelButton;
  public ExamsFromDatabaseInput() {
    initComponents();
  }

  private void okButtonActionPerformed(ActionEvent e) {
    String userName = this.textField1.getName();
    char[] password = this.passwordField1.getPassword();
    String connector = this.textArea1.getText();
    String table = this.textField2.getText();

    user = new SQLLoginUser(userName, password, table, connector);
  }

  public SQLLoginUser getUser() {
    return user;
  }

  private void cancelButtonActionPerformed(ActionEvent e) {
    this.dispose();
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - Edwin Carlsson
    dialogPane = new JPanel();
    contentPanel = new JPanel();
    label1 = new JLabel();
    label2 = new JLabel();
    label3 = new JLabel();
    label5 = new JLabel();
    label4 = new JLabel();
    textField1 = new JTextField();
    passwordField1 = new JPasswordField();
    textField2 = new JTextField();
    scrollPane1 = new JScrollPane();
    textArea1 = new JTextArea();
    buttonBar = new JPanel();
    okButton = new JButton();
    cancelButton = new JButton();

    //======== this ========
    setMinimumSize(new Dimension(700, 300));
    setTitle("Input Database");
    setBackground(new Color(224, 224, 223));
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setResizable(false);
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    //======== dialogPane ========
    {
      dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
      dialogPane.setBackground(new Color(224, 224, 223));
      dialogPane.setForeground(new Color(180, 29, 141));

      // JFormDesigner evaluation mark
      dialogPane.setBorder(new javax.swing.border.CompoundBorder(
          new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
              "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
              javax.swing.border.TitledBorder.BOTTOM,
              new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
              java.awt.Color.red), dialogPane.getBorder()));
      dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
        public void propertyChange(java.beans.PropertyChangeEvent e) {
          if ("border".equals(e.getPropertyName())) {
            throw new RuntimeException();
          }
        }
      });

      dialogPane.setLayout(new BorderLayout());

      //======== contentPanel ========
      {
        contentPanel.setForeground(new Color(180, 29, 141));
        contentPanel.setBackground(new Color(224, 224, 223));
        contentPanel.setLayout(new GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));

        //---- label1 ----
        label1.setText("Sql Input Database Connection Information");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setFont(new Font("TheSans", Font.PLAIN, 18));
        label1.setForeground(new Color(180, 29, 141));
        contentPanel.add(label1, new GridConstraints(0, 0, 1, 4,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

        //---- label2 ----
        label2.setText("Username");
        label2.setFont(new Font("TheSans", Font.PLAIN, 14));
        label2.setForeground(new Color(180, 29, 141));
        label2.setBackground(new Color(224, 224, 223));
        contentPanel.add(label2, new GridConstraints(1, 0, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

        //---- label3 ----
        label3.setText("Password");
        label3.setFont(new Font("TheSans", Font.PLAIN, 14));
        label3.setForeground(new Color(180, 29, 141));
        label3.setBackground(new Color(224, 224, 223));
        contentPanel.add(label3, new GridConstraints(1, 1, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

        //---- label5 ----
        label5.setText("Table Name");
        label5.setFont(new Font("TheSans", Font.PLAIN, 14));
        label5.setForeground(new Color(180, 29, 141));
        label5.setBackground(new Color(224, 224, 223));
        contentPanel.add(label5, new GridConstraints(1, 2, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

        //---- label4 ----
        label4.setText("Connector String");
        label4.setFont(new Font("TheSans", Font.PLAIN, 14));
        label4.setForeground(new Color(180, 29, 141));
        label4.setBackground(new Color(224, 224, 223));
        contentPanel.add(label4, new GridConstraints(1, 3, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

        //---- textField1 ----
        textField1.setText("Username");
        textField1.setFont(new Font("TheSans", Font.PLAIN, 14));
        textField1.setForeground(new Color(180, 29, 141));
        contentPanel.add(textField1, new GridConstraints(2, 0, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

        //---- passwordField1 ----
        passwordField1.setText("Password");
        passwordField1.setFont(new Font("TheSans", Font.PLAIN, 14));
        passwordField1.setForeground(new Color(180, 29, 141));
        passwordField1.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField1.setBackground(new Color(224, 224, 223));
        contentPanel.add(passwordField1, new GridConstraints(2, 1, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, new Dimension(130, 30), null));

        //---- textField2 ----
        textField2.setText("Table Name");
        textField2.setFont(new Font("TheSans", Font.PLAIN, 14));
        textField2.setForeground(new Color(180, 29, 141));
        contentPanel.add(textField2, new GridConstraints(2, 2, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

        //======== scrollPane1 ========
        {
          scrollPane1.setBackground(new Color(224, 224, 223));

          //---- textArea1 ----
          textArea1.setText("Connector String");
          textArea1.setFont(new Font("TheSans", Font.PLAIN, 14));
          textArea1.setForeground(new Color(180, 29, 141));
          textArea1.setBackground(new Color(224, 224, 223));
          scrollPane1.setViewportView(textArea1);
        }
        contentPanel.add(scrollPane1, new GridConstraints(2, 3, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));
      }
      dialogPane.add(contentPanel, BorderLayout.CENTER);

      //======== buttonBar ========
      {
        buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
        buttonBar.setBackground(new Color(224, 224, 223));
        buttonBar.setForeground(new Color(180, 29, 141));
        buttonBar.setLayout(new GridBagLayout());
        ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[]{0, 85, 80};
        ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[]{1.0, 0.0, 0.0};

        //---- okButton ----
        okButton.setText("OK");
        okButton.setForeground(new Color(180, 29, 141));
        okButton.setBackground(new Color(224, 224, 223));
        okButton.setFont(new Font("TheSans", Font.BOLD, 12));
        okButton.addActionListener(e -> okButtonActionPerformed(e));
        buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));

        //---- cancelButton ----
        cancelButton.setText("Cancel");
        cancelButton.setForeground(new Color(180, 29, 141));
        cancelButton.setBackground(new Color(224, 224, 223));
        cancelButton.setFont(new Font("TheSans", Font.BOLD, 12));
        cancelButton.addActionListener(e -> cancelButtonActionPerformed(e));
        buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
      }
      dialogPane.add(buttonBar, BorderLayout.SOUTH);
    }
    contentPane.add(dialogPane, BorderLayout.CENTER);
    pack();
    setLocationRelativeTo(getOwner());
    // JFormDesigner - End of component initialization  //GEN-END:initComponents
  }
  // JFormDesigner - End of variables declaration  //GEN-END:variables
}
