package com.bth.gui;

import com.bth.analysis.Stats.StatsSchool;
import com.bth.analysis.Stats.StatsTeam;
import com.bth.analysis.Stats.helperobjects.RoundOffStatsQuestion;
import com.bth.exams.ExamSchool;
import com.bth.gui.controller.GUIController;
import com.bth.gui.csvchooser.CsvDirectoryChoice;
import com.bth.gui.login.LoginDatabase;
import com.bth.io.database.mongodb.mongodbconnector.MongoDBConnector;
import com.bth.io.database.mongodb.mongodbcontroller.MongoDBController;
import com.bth.io.database.sql.sqlconnector.SQLConnector;
import com.bth.io.database.sql.sqlcontroller.SQLController;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MainGUI {
  public JPanel panel1;
  public JLabel DATABASELabel;
  public JLabel CSVLabel;
  public JButton questionsToDatabaseButton;
  public JButton teamsToDatabaseButton;
  public JButton schoolsToDatabaseButton;
  public JButton exitButton;
  public JButton questionsToCSVButton;
  public JButton teamsToCSVButton;
  public JButton schoolsToCSVButton;
  public JButton oneToFourteenCSVButton;
  public JButton CSVInputFileButton;
  public JButton loginDatabaseButton;

  private GUIController controller;
  private LoginDatabase database;
  private CsvDirectoryChoice csvDirectoryChoice;
  private Connection connection;
  private MongoCollection<BasicDBObject> collection;

  private List<RoundOffStatsQuestion> questionsStats;
  private List<StatsSchool> statsSchools;
  private List<StatsTeam> statsTeams;

  public MainGUI(ExamSchool[] examSchools, List<RoundOffStatsQuestion> questionsStats, List<StatsSchool> statsSchools, List<StatsTeam> statsTeams) {
    this.questionsStats = questionsStats;
    this.statsSchools = statsSchools;
    this.statsTeams = statsTeams;

    controller = new GUIController();
    database = new LoginDatabase();
    csvDirectoryChoice = new CsvDirectoryChoice();

    ArrayList<JComponent> list = addAllToList();
    controller.setEnabledForAll(list, false);

    loginDatabaseButton.addActionListener(e1 ->
        database.frame.setVisible(true));

    database.confirmButton.addActionListener(new DatabaseButtonListener());

    // TODO: 2019-03-20 THIS IS WIP CHANGE THIS!
    teamsToCSVButton.addActionListener(new DatabaseIntegrationListener());

    exitButton.addActionListener(e -> {
      if (SQLConnector.isConnected()) SQLConnector.disconnect();
      if (MongoDBConnector.isConnected()) MongoDBConnector.disconnect();
      System.exit(1);
    });

    CSVInputFileButton.addActionListener(e -> {
      csvDirectoryChoice.frame.setVisible(true);
    });
  }

  private ArrayList<JComponent> addAllToList() {
    ArrayList<JComponent> temp = new ArrayList<>();
    temp.add(questionsToCSVButton);
    temp.add(teamsToCSVButton);
    temp.add(schoolsToCSVButton);
    temp.add(oneToFourteenCSVButton);
    temp.add(schoolsToDatabaseButton);
    temp.add(teamsToDatabaseButton);
    temp.add(questionsToDatabaseButton);
    return temp;
  }

  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer
   * >>> IMPORTANT!! <<<
   * DO NOT edit this method OR call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    panel1 = new JPanel();
    panel1.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel1.setBackground(new Color(-12828863));
    panel1.setMaximumSize(new Dimension(630, 630));
    panel1.setMinimumSize(new Dimension(630, 630));
    panel1.setPreferredSize(new Dimension(630, 630));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    final JPanel panel3 = new JPanel();
    panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel2.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    DATABASELabel = new JLabel();
    DATABASELabel.setBackground(new Color(-12508876));
    DATABASELabel.setForeground(new Color(-11682923));
    DATABASELabel.setHorizontalAlignment(0);
    DATABASELabel.setText("DATABASE");
    panel3.add(DATABASELabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 1, false));
    final JPanel panel4 = new JPanel();
    panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel2.add(panel4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    CSVLabel = new JLabel();
    CSVLabel.setBackground(new Color(-12508876));
    CSVLabel.setForeground(new Color(-11682923));
    CSVLabel.setHorizontalAlignment(0);
    CSVLabel.setText("CSV");
    panel4.add(CSVLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    final JPanel panel5 = new JPanel();
    panel5.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    final JPanel panel6 = new JPanel();
    panel6.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1, true, true));
    panel5.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    questionsToCSVButton = new JButton();
    questionsToCSVButton.setText("Questions To CSV");
    panel6.add(questionsToCSVButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    teamsToCSVButton = new JButton();
    teamsToCSVButton.setText("Teams To CSV");
    panel6.add(teamsToCSVButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    schoolsToCSVButton = new JButton();
    schoolsToCSVButton.setText("Schools To CSV");
    panel6.add(schoolsToCSVButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    oneToFourteenCSVButton = new JButton();
    oneToFourteenCSVButton.setEnabled(true);
    oneToFourteenCSVButton.setText("Q1-Q14 to CSV");
    panel6.add(oneToFourteenCSVButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel7 = new JPanel();
    panel7.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1, true, true));
    panel5.add(panel7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    questionsToDatabaseButton = new JButton();
    questionsToDatabaseButton.setText("Questions To Database");
    panel7.add(questionsToDatabaseButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    teamsToDatabaseButton = new JButton();
    teamsToDatabaseButton.setText("Teams To Database");
    panel7.add(teamsToDatabaseButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    schoolsToDatabaseButton = new JButton();
    schoolsToDatabaseButton.setText("Schools To Database");
    panel7.add(schoolsToDatabaseButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    exitButton = new JButton();
    exitButton.setText("Exit");
    panel7.add(exitButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel8 = new JPanel();
    panel8.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel8, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    CSVInputFileButton = new JButton();
    CSVInputFileButton.setText("Choose CSV File");
    panel8.add(CSVInputFileButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    loginDatabaseButton = new JButton();
    loginDatabaseButton.setText("Login Database");
    panel8.add(loginDatabaseButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return panel1;
  }

  // Inner class for Database integration
  private class CSVButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      String input = e.getActionCommand();
    }
  }

  // Inner class for Database integration
  class DatabaseIntegrationListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        if (GUIController.dbChoice) // SQL insertion
        {
          connection = database.getConnection();
          if (connection != null) {
            System.out.println("Inserting StatsTeams into " + SQLConnector.databaseLoginUser.getTypeDatabase() + " database...");
            SQLController.insertIntoDatabase(connection, statsTeams, null, null);
            System.out.println("Inserted!");
          }
        } else // MongoDB insertion
        {
          // TODO: 2019-03-20 Insert mongodb.
          System.out.println("Inserting StatsSchools into " + MongoDBConnector.user.getTypeDatabase() + " database...");
          collection = database.getObjectsInDb();

          MongoDBController.insertIntoMongoDatabase(collection, statsSchools);
          System.out.println("Inserted!");
        }
      } catch (SQLException exc) {
        exc.printStackTrace();
      }
    }
  }

  // Inner class for Database login
  class DatabaseButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      controller.setEnabledForAll(addAllToList(), true);
      CSVInputFileButton.setEnabled(true);
    }
  }

}