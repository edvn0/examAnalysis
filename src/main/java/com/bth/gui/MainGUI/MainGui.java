/*
 * Copyright (c) 2018-2019 Edwin Carlsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.bth.gui.MainGUI;

import com.bth.analysis.ExamAnalysis;
import com.bth.analysis.stats.StatsSchool;
import com.bth.analysis.stats.StatsTeam;
import com.bth.analysis.stats.helperobjects.RoundOffStatsQuestion;
import com.bth.exams.ExamSchool;
import com.bth.gui.controller.GuiController;
import com.bth.gui.controller.loginusers.SQLLoginUser;
import com.bth.gui.csvchooser.CsvDirectoryChoice;
import com.bth.gui.dbinput.ExamsFromDatabaseInput;
import com.bth.gui.examdirectorygui.ChooseInputFileFrame;
import com.bth.gui.login.LoginDatabase;
import com.bth.io.output.ExamOutput;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class MainGui extends JPanel {

  private static File file;

  private GuiController controller;
  private ExamAnalysis examAnalysis;

  private List<RoundOffStatsQuestion> questionsStats;
  private List<StatsSchool> statsSchools;
  private List<StatsTeam> statsTeams;
  private ExamSchool[] exams;

  private JFrame frame;

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  // Generated using JFormDesigner Evaluation license - Edwin Carlsson
  private JButton loginDatabaseButton;

  private void initArrays() {
    this.questionsStats = examAnalysis.getQuestionsStats();
    this.statsSchools = examAnalysis.getStatsSchools();
    this.statsTeams = examAnalysis.getStatsTeams();
    this.exams = examAnalysis.getExamSchools();
  }

  private ArrayList<JComponent> addAllToList() {
    ArrayList<JComponent> temp = new ArrayList<>();
    temp.add(getQuestionsToCSVButton());
    temp.add(getTeamsToCSVButton());
    temp.add(getSchoolsToCSVButton());
    temp.add(getOneToFourteenCSVButton());
    temp.add(getSchoolsToDatabaseButton());
    temp.add(getTeamsToDatabaseButton());
    temp.add(getQuestionsToDatabaseButton());
    temp.add(insertEverythingButton);
    return temp;
  }

  private void insertActionListener(ArrayList<JComponent> components) {
    for (JComponent component : components) {
      JButton button = (JButton) component;
      button.addActionListener(new OutputIntegrationListener());
    }
  }

  public JButton getLoginDatabaseButton() {
    return loginDatabaseButton;
  }

  public JLabel getCSVLabel() {
    return CSVLabel;
  }

  public JButton getQuestionsToDatabaseButton() {
    return questionsToDatabaseButton;
  }

  public JButton getTeamsToDatabaseButton() {
    return teamsToDatabaseButton;
  }

  public JButton getSchoolsToDatabaseButton() {
    return schoolsToDatabaseButton;
  }

  public JButton getExitButton() {
    return exitButton;
  }

  public JButton getQuestionsToCSVButton() {
    return questionsToCSVButton;
  }

  public JButton getTeamsToCSVButton() {
    return teamsToCSVButton;
  }

  public JButton getSchoolsToCSVButton() {
    return schoolsToCSVButton;
  }

  public JButton getOneToFourteenCSVButton() {
    return oneToFourteenCSVButton;
  }

  public JButton getCSVInputFileButton() {
    return CSVInputFileButton;
  }

  private Component getFrame() {
    return this.frame;
  }

  public static void setFileFromChooseDirectory(File file) {
    MainGui.file = file;
  }
  private JLabel DATABASELabel;
  private JLabel CSVLabel;
  private JButton CSVInputFileButton;
  private JButton exitButton;

  // Inner class for Database login
  class DatabaseButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      controller.setEnabledForAll(addAllToList(), true);
      getCSVInputFileButton().setEnabled(true);
    }

  }
  private JPanel databasePanel;
  private JPanel dbPanel;
  private JButton teamsToDatabaseButton;
  private JButton questionsToDatabaseButton;
  private JButton insertEverythingButton;
  private JButton schoolsToDatabaseButton;
  private JPanel csvPanel;
  private JButton oneToFourteenCSVButton;
  private JButton questionsToCSVButton;
  private JButton schoolsToCSVButton;
  private JButton teamsToCSVButton;
  public MainGui() throws FileNotFoundException {
    initComponents();
    controller = new GuiController();
    setup();
  }

  private void setup() throws FileNotFoundException {
    // Init gui
    frame = new JFrame("MainGui");
    frame.setLocationByPlatform(true);
    frame.setContentPane(this);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);
    controller.append(this);
    controller.getMainGui().getFrame().setVisible(false);
    // End init gui

    // Add all relevant components to a List to manage more easily.
    ArrayList<JComponent> list = addAllToList();
    // Disable prior to initialisation.
    controller.setEnabledForAll(list, false);
    controller.append(new LoginDatabase());
    controller.append(new ChooseInputFileFrame());
    controller.append(new CsvDirectoryChoice());
    controller.append(new ExamsFromDatabaseInput());

    // Invisible until resource directory has been chosen.
    controller.getCsvDirectoryChoice().setVisible(false);

    // Init all the JButton listeners
    this.insertActionListener(list);

    getLoginDatabaseButton().addActionListener(this::actionPerformed);

    controller.getLoginDatabase().getConfirmButton()
        .addActionListener(new DatabaseButtonListener());

    getCSVInputFileButton().addActionListener(this::actionPerformed2);

    getExitButton().addActionListener(e -> System.exit(0));

    if (file != null) {
      // File input
      examAnalysis = new ExamAnalysis(file.getAbsolutePath());

    } else {
      SQLLoginUser user = controller.getExamsFromDatabaseInput().getUser();
      examAnalysis = new ExamAnalysis(user);
    }
    examAnalysis.start();
    this.initArrays();
    this.frame.setVisible(true);
  }

  private void actionPerformed(ActionEvent e1) {
    controller.getLoginDatabase().getFrame().setVisible(true);
  }

  private void actionPerformed2(ActionEvent e) {
    controller.getCsvDirectoryChoice().init();
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - Edwin Carlsson
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    loginDatabaseButton = new JButton();
    DATABASELabel = new JLabel();
    JPanel panel3 = new JPanel();
    CSVLabel = new JLabel();
    CSVInputFileButton = new JButton();
    exitButton = new JButton();
    JPanel panel4 = new JPanel();
    databasePanel = new JPanel();
    dbPanel = new JPanel();
    teamsToDatabaseButton = new JButton();
    questionsToDatabaseButton = new JButton();
    insertEverythingButton = new JButton();
    schoolsToDatabaseButton = new JButton();
    csvPanel = new JPanel();
    oneToFourteenCSVButton = new JButton();
    questionsToCSVButton = new JButton();
    schoolsToCSVButton = new JButton();
    teamsToCSVButton = new JButton();

    //======== this ========
    setBackground(new Color(224, 224, 223));
    setForeground(new Color(180, 29, 141));
    setMaximumSize(new Dimension(800, 630));
    setMinimumSize(new Dimension(800, 630));
    setPreferredSize(new Dimension(800, 630));

    // JFormDesigner evaluation mark
    setBorder(new javax.swing.border.CompoundBorder(
        new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
            "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.BOTTOM,
            new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
            java.awt.Color.red), getBorder()));
    addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      public void propertyChange(java.beans.PropertyChangeEvent e) {
        if ("border".equals(e.getPropertyName())) {
          throw new RuntimeException();
        }
      }
    });

    setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));

    //======== panel1 ========
    {
      panel1.setBackground(new Color(224, 224, 223));
      panel1.setForeground(new Color(180, 29, 141));
      panel1.setBorder(new TitledBorder(new LineBorder(new Color(180, 29, 141)), ""));
      panel1.setLayout(new GridLayoutManager(1, 2, new Insets(8, 8, 8, 8), -1, -1, true, false));

      //======== panel2 ========
      {
        panel2.setAutoscrolls(false);
        panel2.setBackground(new Color(224, 224, 223));
        panel2.setInheritsPopupMenu(false);
        panel2.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED), ""));
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), 0, 0));

        //---- loginDatabaseButton ----
        loginDatabaseButton.setBackground(new Color(224, 224, 223));
        loginDatabaseButton
            .setFont(new Font("TheSans", loginDatabaseButton.getFont().getStyle(), 14));
        loginDatabaseButton.setForeground(new Color(180, 29, 141));
        loginDatabaseButton.setHorizontalAlignment(SwingConstants.CENTER);
        loginDatabaseButton.setHorizontalTextPosition(SwingConstants.CENTER);
        loginDatabaseButton.setText("Login to Database");
        panel2.add(loginDatabaseButton, new GridConstraints(1, 0, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            new Dimension(200, 30), new Dimension(287, 30), new Dimension(200, 30)));

        //---- DATABASELabel ----
        DATABASELabel.setAlignmentX(0.5F);
        DATABASELabel.setBackground(new Color(224, 224, 223));
        DATABASELabel.setDoubleBuffered(true);
        DATABASELabel.setFont(new Font("TheSans", Font.BOLD, 24));
        DATABASELabel.setForeground(new Color(180, 29, 141));
        DATABASELabel.setHorizontalAlignment(SwingConstants.CENTER);
        DATABASELabel.setHorizontalTextPosition(SwingConstants.CENTER);
        DATABASELabel.setOpaque(false);
        DATABASELabel.setText("DATABASE");
        DATABASELabel.setVerticalAlignment(SwingConstants.CENTER);
        DATABASELabel.setVerticalTextPosition(SwingConstants.CENTER);
        panel2.add(DATABASELabel, new GridConstraints(0, 0, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            new Dimension(200, 30), new Dimension(200, 30), new Dimension(200, 30)));
      }
      panel1.add(panel2, new GridConstraints(0, 0, 1, 1,
          GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          null, null, null));

      //======== panel3 ========
      {
        panel3.setAutoscrolls(false);
        panel3.setBackground(new Color(224, 224, 223));
        panel3.setInheritsPopupMenu(false);
        panel3.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED), ""));
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), 0, 0));

        //---- CSVLabel ----
        CSVLabel.setAlignmentX(0.5F);
        CSVLabel.setBackground(new Color(224, 224, 223));
        CSVLabel.setDoubleBuffered(true);
        CSVLabel.setFont(new Font("TheSans", Font.BOLD, 24));
        CSVLabel.setForeground(new Color(180, 29, 141));
        CSVLabel.setHorizontalAlignment(SwingConstants.CENTER);
        CSVLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        CSVLabel.setOpaque(false);
        CSVLabel.setText("CSV");
        CSVLabel.setVerticalAlignment(SwingConstants.CENTER);
        CSVLabel.setVerticalTextPosition(SwingConstants.CENTER);
        panel3.add(CSVLabel, new GridConstraints(0, 0, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            new Dimension(200, 30), new Dimension(200, 30), new Dimension(200, 30)));

        //---- CSVInputFileButton ----
        CSVInputFileButton.setBackground(new Color(224, 224, 223));
        CSVInputFileButton
            .setFont(new Font("TheSans", CSVInputFileButton.getFont().getStyle(), 14));
        CSVInputFileButton.setForeground(new Color(180, 29, 141));
        CSVInputFileButton.setHorizontalAlignment(SwingConstants.CENTER);
        CSVInputFileButton.setHorizontalTextPosition(SwingConstants.CENTER);
        CSVInputFileButton.setText("Choose CSV File");
        panel3.add(CSVInputFileButton, new GridConstraints(1, 0, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            new Dimension(200, 30), new Dimension(200, 30), new Dimension(200, 30)));
      }
      panel1.add(panel3, new GridConstraints(0, 1, 1, 1,
          GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          null, null, null));
    }
    add(panel1, new GridConstraints(0, 0, 1, 1,
        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
        GridConstraints.SIZEPOLICY_FIXED,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        null, null, null));

    //---- exitButton ----
    exitButton.setBackground(new Color(224, 224, 223));
    exitButton.setFont(new Font("TheSans", exitButton.getFont().getStyle(), 14));
    exitButton.setForeground(new Color(180, 29, 141));
    exitButton.setText("Exit");
    add(exitButton, new GridConstraints(2, 0, 1, 1,
        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED,
        null, null, null));

    //======== panel4 ========
    {
      panel4.setBackground(new Color(224, 224, 223));
      panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));

      //======== databasePanel ========
      {
        databasePanel.setBackground(new Color(224, 224, 223));
        databasePanel.setOpaque(true);
        databasePanel.setBorder(new TitledBorder(new LineBorder(new Color(180, 29, 141)), ""));
        databasePanel.setLayout(new GridLayoutManager(1, 2, new Insets(10, 10, 10, 10), -1, -1));

        //======== dbPanel ========
        {
          dbPanel.setBackground(new Color(224, 224, 223));
          dbPanel.setBorder(new TitledBorder(new LineBorder(new Color(180, 29, 141)), ""));
          dbPanel.setLayout(new GridLayoutManager(4, 1, new Insets(8, 8, 8, 8), -1, -1));

          //---- teamsToDatabaseButton ----
          teamsToDatabaseButton.setBackground(new Color(224, 224, 223));
          teamsToDatabaseButton.setBorderPainted(true);
          teamsToDatabaseButton
              .setFont(new Font("TheSans", teamsToDatabaseButton.getFont().getStyle(), 14));
          teamsToDatabaseButton.setForeground(new Color(180, 29, 141));
          teamsToDatabaseButton.setHorizontalAlignment(SwingConstants.CENTER);
          teamsToDatabaseButton.setHorizontalTextPosition(SwingConstants.CENTER);
          teamsToDatabaseButton.setText("Teams To Database");
          teamsToDatabaseButton.setToolTipText("Inserts the teams into the database/s.");
          teamsToDatabaseButton.setVerticalAlignment(SwingConstants.CENTER);
          teamsToDatabaseButton.setVerticalTextPosition(SwingConstants.CENTER);
          dbPanel.add(teamsToDatabaseButton, new GridConstraints(1, 0, 1, 1,
              GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_FIXED,
              new Dimension(200, 30), new Dimension(200, 30), new Dimension(200, 30)));

          //---- questionsToDatabaseButton ----
          questionsToDatabaseButton.setBackground(new Color(224, 224, 223));
          questionsToDatabaseButton.setBorderPainted(true);
          questionsToDatabaseButton
              .setFont(new Font("TheSans", questionsToDatabaseButton.getFont().getStyle(), 14));
          questionsToDatabaseButton.setForeground(new Color(180, 29, 141));
          questionsToDatabaseButton.setHorizontalAlignment(SwingConstants.CENTER);
          questionsToDatabaseButton.setHorizontalTextPosition(SwingConstants.CENTER);
          questionsToDatabaseButton.setText("Questions To Database");
          questionsToDatabaseButton.setToolTipText("Inserts the questions into the database/s.");
          questionsToDatabaseButton.setVerticalAlignment(SwingConstants.CENTER);
          questionsToDatabaseButton.setVerticalTextPosition(SwingConstants.CENTER);
          dbPanel.add(questionsToDatabaseButton, new GridConstraints(2, 0, 1, 1,
              GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_FIXED,
              new Dimension(200, 30), new Dimension(200, 30), new Dimension(200, 30)));

          //---- insertEverythingButton ----
          insertEverythingButton.setBackground(new Color(224, 224, 223));
          insertEverythingButton.setBorderPainted(true);
          insertEverythingButton
              .setFont(new Font("TheSans", insertEverythingButton.getFont().getStyle(), 14));
          insertEverythingButton.setForeground(new Color(180, 29, 141));
          insertEverythingButton.setHorizontalAlignment(SwingConstants.CENTER);
          insertEverythingButton.setHorizontalTextPosition(SwingConstants.CENTER);
          insertEverythingButton.setText("Insert everything");
          insertEverythingButton.setToolTipText(
              "Inserts the information about the schools, teams and questions into both databases at the same time.");
          insertEverythingButton.setVerticalAlignment(SwingConstants.CENTER);
          insertEverythingButton.setVerticalTextPosition(SwingConstants.CENTER);
          dbPanel.add(insertEverythingButton, new GridConstraints(3, 0, 1, 1,
              GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_FIXED,
              new Dimension(200, 30), new Dimension(200, 30), new Dimension(200, 30)));

          //---- schoolsToDatabaseButton ----
          schoolsToDatabaseButton.setBackground(new Color(224, 224, 223));
          schoolsToDatabaseButton.setBorderPainted(true);
          schoolsToDatabaseButton
              .setFont(new Font("TheSans", schoolsToDatabaseButton.getFont().getStyle(), 14));
          schoolsToDatabaseButton.setForeground(new Color(180, 29, 141));
          schoolsToDatabaseButton.setHorizontalAlignment(SwingConstants.CENTER);
          schoolsToDatabaseButton.setHorizontalTextPosition(SwingConstants.CENTER);
          schoolsToDatabaseButton.setText("Schools To Database");
          schoolsToDatabaseButton.setToolTipText("Inserts the schools into the database/s.");
          schoolsToDatabaseButton.setVerticalAlignment(SwingConstants.CENTER);
          schoolsToDatabaseButton.setVerticalTextPosition(SwingConstants.CENTER);
          dbPanel.add(schoolsToDatabaseButton, new GridConstraints(0, 0, 1, 1,
              GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_FIXED,
              new Dimension(200, 30), new Dimension(200, 30), new Dimension(200, 30)));
        }
        databasePanel.add(dbPanel, new GridConstraints(0, 0, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

        //======== csvPanel ========
        {
          csvPanel.setBackground(new Color(224, 224, 223));
          csvPanel.setBorder(new TitledBorder(new LineBorder(new Color(180, 29, 141)), ""));
          csvPanel.setLayout(new GridLayoutManager(4, 1, new Insets(8, 8, 8, 8), -1, -1));

          //---- oneToFourteenCSVButton ----
          oneToFourteenCSVButton.setBackground(new Color(224, 224, 223));
          oneToFourteenCSVButton.setBorderPainted(true);
          oneToFourteenCSVButton.setEnabled(true);
          oneToFourteenCSVButton
              .setFont(new Font("TheSans", oneToFourteenCSVButton.getFont().getStyle(), 14));
          oneToFourteenCSVButton.setForeground(new Color(180, 29, 141));
          oneToFourteenCSVButton.setHorizontalAlignment(SwingConstants.CENTER);
          oneToFourteenCSVButton.setHorizontalTextPosition(SwingConstants.CENTER);
          oneToFourteenCSVButton.setText("Q1-Q14 to CSV");
          oneToFourteenCSVButton.setVerticalAlignment(SwingConstants.CENTER);
          oneToFourteenCSVButton.setVerticalTextPosition(SwingConstants.CENTER);
          csvPanel.add(oneToFourteenCSVButton, new GridConstraints(2, 0, 1, 1,
              GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_FIXED,
              new Dimension(200, 30), new Dimension(200, 30), new Dimension(200, 30)));

          //---- questionsToCSVButton ----
          questionsToCSVButton.setBackground(new Color(224, 224, 223));
          questionsToCSVButton.setBorderPainted(true);
          questionsToCSVButton
              .setFont(new Font("TheSans", questionsToCSVButton.getFont().getStyle(), 14));
          questionsToCSVButton.setForeground(new Color(180, 29, 141));
          questionsToCSVButton.setHorizontalAlignment(SwingConstants.CENTER);
          questionsToCSVButton.setHorizontalTextPosition(SwingConstants.CENTER);
          questionsToCSVButton.setText("Questions To CSV");
          questionsToCSVButton.setVerticalAlignment(SwingConstants.CENTER);
          questionsToCSVButton.setVerticalTextPosition(SwingConstants.CENTER);
          csvPanel.add(questionsToCSVButton, new GridConstraints(3, 0, 1, 1,
              GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_FIXED,
              new Dimension(200, 30), new Dimension(200, 30), new Dimension(200, 30)));

          //---- schoolsToCSVButton ----
          schoolsToCSVButton.setBackground(new Color(224, 224, 223));
          schoolsToCSVButton.setBorderPainted(true);
          schoolsToCSVButton
              .setFont(new Font("TheSans", schoolsToCSVButton.getFont().getStyle(), 14));
          schoolsToCSVButton.setForeground(new Color(180, 29, 141));
          schoolsToCSVButton.setHorizontalAlignment(SwingConstants.CENTER);
          schoolsToCSVButton.setHorizontalTextPosition(SwingConstants.CENTER);
          schoolsToCSVButton.setText("Schools To CSV");
          schoolsToCSVButton.setVerticalAlignment(SwingConstants.CENTER);
          schoolsToCSVButton.setVerticalTextPosition(SwingConstants.CENTER);
          csvPanel.add(schoolsToCSVButton, new GridConstraints(0, 0, 1, 1,
              GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_FIXED,
              new Dimension(200, 30), new Dimension(200, 30), new Dimension(200, 30)));

          //---- teamsToCSVButton ----
          teamsToCSVButton.setBackground(new Color(224, 224, 223));
          teamsToCSVButton.setBorderPainted(true);
          teamsToCSVButton.setFont(new Font("TheSans", teamsToCSVButton.getFont().getStyle(), 14));
          teamsToCSVButton.setForeground(new Color(180, 29, 141));
          teamsToCSVButton.setHorizontalAlignment(SwingConstants.CENTER);
          teamsToCSVButton.setHorizontalTextPosition(SwingConstants.CENTER);
          teamsToCSVButton.setText("Teams To CSV");
          teamsToCSVButton.setVerticalAlignment(SwingConstants.CENTER);
          teamsToCSVButton.setVerticalTextPosition(SwingConstants.CENTER);
          csvPanel.add(teamsToCSVButton, new GridConstraints(1, 0, 1, 1,
              GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
              GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
              GridConstraints.SIZEPOLICY_FIXED,
              new Dimension(200, 30), new Dimension(200, 30), new Dimension(200, 30)));
        }
        databasePanel.add(csvPanel, new GridConstraints(0, 1, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));
      }
      panel4.add(databasePanel, new GridConstraints(0, 0, 1, 1,
          GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          null, null, null));
    }
    add(panel4, new GridConstraints(1, 0, 1, 1,
        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        null, null, null));
    // JFormDesigner - End of component initialization  //GEN-END:initComponents
  }

  // Inner class for Database integration
  class OutputIntegrationListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      // These booleans represent the state of the database login:s.
      boolean[] loggedIn = new boolean[]{
          controller.getMDBConnection() != null && controller.getMySqlConnection() != null,
          controller.getMDBConnection() != null && controller.getMySqlConnection() == null,
          controller.getMDBConnection() == null && controller.getMySqlConnection() != null
      };

      String directory = ExamOutput.getDirectory();

      switch (e.getActionCommand().toLowerCase().trim()) {
        case ("teams to csv"):
          System.out.println("*-------------------------------------------------------*");
          System.out.println("Printing StatsTeam information to " + directory + " ...");
          System.out.println("*-------------------------------------------------------*");
          ExamOutput.printToCSV_Teams(statsTeams);
          System.out.println("Printed StatsTeam information!");
          break;
        case ("schools to csv"):
          System.out.println("*-------------------------------------------------------*");
          System.out.println("Printing StatsSchool information to " + directory + " ...");
          System.out.println("*-------------------------------------------------------*");
          ExamOutput.printToCSV_Schools(statsSchools);
          System.out.println("Printed StatsSchool information!");
          break;
        case ("questions to csv"):
          System.out.println("*-------------------------------------------------------*");
          System.out.println("Printing StatsQuestion information to " + directory + " ...");
          System.out.println("*-------------------------------------------------------*");
          ExamOutput.printToCSV_Questions(questionsStats);
          System.out.println("Printed StatsQuestion information!");
          break;
        case ("q1-q14 to csv"):
          System.out.println("*-------------------------------------------------------*");
          System.out.println("Printing question 1-14 into " + directory + " ...");
          System.out.println("*-------------------------------------------------------*");
          ExamOutput.printQuestionsToCSV(exams, examAnalysis.getExamTeams());
          System.out.println("Printed q1-q14 information!");
          break;
        case ("questions to database"):
          if (loggedIn[2]) // SQL insertion StatsQuestions
          {
            System.out.println("*-------------------------------------------------------*");
            System.out.println("Inserting " + e.getActionCommand() + " into database...");
            System.out.println("*-------------------------------------------------------*");

            try {
              controller.insertIntoMySQLDatabase(null,
                  null, questionsStats,
                  controller.getLoginDatabase().getMySqlConnection().getUser().getQuestionTable());
            } catch (SQLException e1) {
              e1.printStackTrace();
            }
            System.out.println("Inserted!");

          } else if (loggedIn[1])// MongoDB insertion StatsQuestions
          {
            System.out.println("*-------------------------------------------------------*");
            System.out.println("Inserting " + e.getActionCommand() + " into database...");
            System.out.println("*-------------------------------------------------------*");

            controller.insertIntoMongoDatabase(null, null, questionsStats,
                null,
                null,
                controller.getLoginDatabase().getMongoDBConnection().getUser().getQuestionColl());

            System.out.println("Inserted!");

          } else if (loggedIn[0]) { // Both insertion StatsQuestions

            System.out.println("*-------------------------------------------------------*");
            System.out.println("Inserting " + e.getActionCommand() + " into both databases...");
            System.out.println("*-------------------------------------------------------*");

            controller.insertIntoMongoDatabase(null, null, questionsStats,
                null,
                null,
                controller.getLoginDatabase().getMongoDBConnection().getUser().getQuestionColl());

            try {
              controller.insertIntoMySQLDatabase(null,
                  null, questionsStats,
                  controller.getLoginDatabase().getMySqlConnection().getUser().getQuestionTable());
            } catch (SQLException ex) {
              ex.printStackTrace();
            }
            System.out.println("Inserted into both databases.");
          }
          break;
        case ("schools to database"):
          if (loggedIn[2]) // SQL insertion StatsSchool
          {
            System.out.println("*-------------------------------------------------------*");
            System.out.println("Inserting " + e.getActionCommand() + " into database...");
            System.out.println("*-------------------------------------------------------*");

            try {
              controller.insertIntoMySQLDatabase(null,
                  statsSchools, null,
                  controller.getLoginDatabase().getMySqlConnection().getUser().getSchoolTable());
            } catch (SQLException e1) {
              e1.printStackTrace();
            }

            System.out.println("Inserted!");

          } else if (loggedIn[1])// MongoDB insertion StatsSchool
          {
            System.out.println("*-------------------------------------------------------*");
            System.out.println("Inserting " + e.getActionCommand() + " into database...");
            System.out.println("*-------------------------------------------------------*");

            controller.insertIntoMongoDatabase(statsSchools, null, null,
                controller.getLoginDatabase().getMongoDBConnection().getUser().getSchoolColl(),
                null,
                null);

            System.out.println("Inserted!");
          } else if (loggedIn[0]) {
            System.out.println("*-------------------------------------------------------*");
            System.out.println("Inserting " + e.getActionCommand() + " into both databases...");
            System.out.println("*-------------------------------------------------------*");

            controller.insertIntoMongoDatabase(statsSchools, null, null,
                controller.getLoginDatabase().getMongoDBConnection().getUser()
                    .getSchoolColl(),
                null,
                null);

            try {
              controller.insertIntoMySQLDatabase(null,
                  statsSchools, null,
                  controller.getLoginDatabase().getMySqlConnection().getUser().getSchoolTable());
            } catch (SQLException e1) {
              e1.printStackTrace();
            }
            System.out.println("Inserted into both databases.");
          }
          break;
        case ("teams to database"):
          if (loggedIn[2]) // SQL insertion StatsTeams
          {
            System.out.println("*-------------------------------------------------------*");
            System.out.println("Inserting " + e.getActionCommand() + " into database...");
            System.out.println("*-------------------------------------------------------*");

            try {
              controller.insertIntoMySQLDatabase(statsTeams,
                  null, null,
                  controller.getLoginDatabase().getMySqlConnection().getUser().getTeamTable());
            } catch (SQLException e1) {
              e1.printStackTrace();
            }

            System.out.println("Inserted!");
          } else if (loggedIn[1])// MongoDB insertion StatsTeams
          {
            System.out.println("*-------------------------------------------------------*");
            System.out.println("Inserting " + e.getActionCommand() + " into database...");
            System.out.println("*-------------------------------------------------------*");

            controller.insertIntoMongoDatabase(null, statsTeams, null,
                null,
                controller.getLoginDatabase().getMongoDBConnection().getUser().getTeamColl(),
                null);

            System.out.println("Inserted!");
          } else if (loggedIn[0]) {
            System.out.println("*-------------------------------------------------------*");
            System.out.println("Inserting " + e.getActionCommand() + " into both databases...");
            System.out.println("*-------------------------------------------------------*");

            controller.insertIntoMongoDatabase(null, statsTeams, null,
                null,
                controller.getLoginDatabase().getMongoDBConnection().getUser().getTeamColl(),
                null);

            try {
              controller.insertIntoMySQLDatabase(statsTeams,
                  null, null,
                  controller.getLoginDatabase().getMySqlConnection().getUser().getTeamTable());
            } catch (SQLException e1) {
              e1.printStackTrace();
            }

            System.out.println("Inserted into both databases.");
          }
          break;
        case "insert everything":

          if (!loggedIn[0]) {
            System.out.println("You are not connected to both databases, "
                + "this button needs you to connect to both databases.");
            break;
          }

          System.out.println("*-------------------------------------------------------*");
          System.out.println(
              "Inserting school, team and questions info into both sql and mongodb databases...");
          System.out.println("*-------------------------------------------------------*");
          try {
            System.out.println("Inserting questions into SQL...");
            controller.insertIntoMySQLDatabase(
                null,
                null,
                questionsStats,
                controller.getLoginDatabase().getMySqlConnection().getUser().getQuestionTable());
            System.out.println("Inserted questions...");
            System.out.println("Inserting questions into Mongo...");

            controller.insertIntoMongoDatabase(
                null,
                null,
                questionsStats,
                null,
                null,
                controller.getLoginDatabase().getMongoDBConnection().getUser()
                    .getQuestionColl());
            System.out.println("Inserted questions...");
            System.out.println("Inserting school stats into SQL...");

            controller.insertIntoMySQLDatabase(
                null,
                statsSchools,
                null,
                controller.getLoginDatabase().getMySqlConnection().getUser().getSchoolTable());
            System.out.println("Inserted school stats...");
            System.out.println("Inserting school stats into Mongo...");

            controller.insertIntoMongoDatabase(
                statsSchools,
                null,
                null,
                controller.getLoginDatabase().getMongoDBConnection().getUser()
                    .getSchoolColl(),
                null,
                null);
            System.out.println("Inserted school stats...");
            System.out.println("Inserting stats teams into SQL...");

            controller.insertIntoMySQLDatabase(
                statsTeams,
                null,
                null,
                controller.getLoginDatabase().getMySqlConnection().getUser().getTeamTable());
            System.out.println("Inserted stats teams...");
            System.out.println("Inserting stats teams into Mongo...");

            controller.insertIntoMongoDatabase(
                null,
                statsTeams,
                null,
                null,
                controller.getLoginDatabase().getMongoDBConnection().getUser()
                    .getTeamColl(),
                null);
            System.out.println("Inserted stats teams...");
          } catch (SQLException e2) {
            e2.printStackTrace();
          }
          System.out.println("Finished successfully!");
          break;
        default:
          break;
      }
    }
  }
  // JFormDesigner - End of variables declaration  //GEN-END:variables
}
