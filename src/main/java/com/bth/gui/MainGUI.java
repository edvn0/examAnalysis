package com.bth.gui;

import com.bth.analysis.Stats.StatsSchool;
import com.bth.analysis.Stats.StatsTeam;
import com.bth.analysis.Stats.helperobjects.RoundOffStatsQuestion;
import com.bth.exams.ExamSchool;
import com.bth.gui.controller.GUIController;
import com.bth.gui.csvchooser.CsvDirectoryChoice;
import com.bth.gui.login.LoginDatabase;
import com.bth.io.ExamOutput;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;


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

  private List<RoundOffStatsQuestion> questionsStats;
  private List<StatsSchool> statsSchools;
  private List<StatsTeam> statsTeams;
  private ExamSchool[] exams;

  public MainGUI(ExamSchool[] exams, List<RoundOffStatsQuestion> questionsStats,
      List<StatsSchool> statsSchools, List<StatsTeam> statsTeams) {
    this.questionsStats = questionsStats;
    this.statsSchools = statsSchools;
    this.statsTeams = statsTeams;
    this.exams = exams;

    controller = new GUIController();

    setup();
  }

  private void setup() {
    ArrayList<JComponent> list = addAllToList();
    controller.setEnabledForAll(list, false);

    controller.append(new LoginDatabase());
    controller.append(new CsvDirectoryChoice());

    this.insertActionListener(list);

    loginDatabaseButton.addActionListener(e1 ->
        controller.getLoginDatabase().getFrame().setVisible(true));

    controller.getLoginDatabase().getConfirmButton().addActionListener(
        new DatabaseButtonListener());

    CSVInputFileButton
        .addActionListener(e -> controller.getCsvDirectoryChoice().getFrame().setVisible(true));

    exitButton.addActionListener(e -> System.exit(0));
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

  private void insertActionListener(ArrayList<JComponent> components) {
    for (JComponent component : components) {
      JButton button = (JButton) component;
      button.addActionListener(new OutputIntegrationListener());
    }
  }

  // Inner class for Database integration
  class OutputIntegrationListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      String directory = ExamOutput.getDirectory();

      switch (e.getActionCommand().toLowerCase().trim()) {
        case ("teams to csv"):
          System.out.println("Printing StatsTeam information to " + directory + " ...");
          ExamOutput.printToCSV_Teams(statsTeams);
          System.out.println("Printed!");
          break;
        case ("schools to csv"):
          System.out.println("Printing StatsSchool information to " + directory + " ...");
          ExamOutput.printToCSV_Schools(statsSchools);
          System.out.println("Printed!");
          break;
        case ("questions to csv"):
          System.out.println("Printing StatsQuestion information to " + directory + " ...");
          ExamOutput.printToCSV_Questions(questionsStats);
          System.out.println("Printed!");
          break;
        case ("q1-q14 to csv"):
          System.out.println("Printing question 1-14 into " + directory + " ...");
          ExamOutput.printQuestionsToCSV(exams);
          System.out.println("Printing!");
          break;
        case ("questions to database"):
          if (GUIController.dbChoice) // SQL insertion StatsQuestions
          {
            System.out.println("Inserting " + e.getActionCommand() + " into database...");

            try {
              controller.insertIntoMySQLDatabase(null,
                  null, questionsStats,
                  controller.getLoginDatabase().getMySqlConnection().getUser().getQuestionTable());
            } catch (SQLException e1) {
              e1.printStackTrace();
            }

            System.out.println("Inserted!");

          } else // MongoDB insertion StatsQuestions
          {
            System.out.println("Inserting " + e.getActionCommand() + " into database...");

            controller.insertIntoMongoDatabase(null, null, questionsStats,
                null,
                null,
                controller.getLoginDatabase().getMongoDBConnection().getUser().getQuestionTable());

            System.out.println("Inserted!");
          }
          break;
        case ("schools to database"):
          if (GUIController.dbChoice) // SQL insertion StatsSchool
          {
            System.out.println("Inserting " + e.getActionCommand() + " into database...");

            try {
              controller.insertIntoMySQLDatabase(null,
                  statsSchools, null,
                  controller.getLoginDatabase().getMySqlConnection().getUser().getSchoolTable());
            } catch (SQLException e1) {
              e1.printStackTrace();
            }

            System.out.println("Inserted!");

          } else // MongoDB insertion StatsSchool
          {
            System.out.println("Inserting " + e.getActionCommand() + " into database...");

            controller.insertIntoMongoDatabase(statsSchools, null, null,
                controller.getLoginDatabase().getMongoDBConnection().getUser()
                    .getSchoolCollection(),
                null,
                null);

            System.out.println("Inserted!");
          }
          break;
        case ("teams to database"):
          if (GUIController.dbChoice) // SQL insertion StatsTeams
          {
            System.out.println("Inserting " + e.getActionCommand() + " into database...");

            try {
              controller.insertIntoMySQLDatabase(statsTeams,
                  null, null,
                  controller.getLoginDatabase().getMySqlConnection().getUser().getTeamTable());
            } catch (SQLException e1) {
              e1.printStackTrace();
            }

            System.out.println("Inserted!");
          } else // MongoDB insertion StatsTeams
          {
            System.out.println("Inserting " + e.getActionCommand() + " into database...");

            controller.insertIntoMongoDatabase(null, statsTeams, null,
                null,
                controller.getLoginDatabase().getMongoDBConnection().getUser().getTeamCollection(),
                null);

            System.out.println("Inserted!");
          }
          break;
        default:
          break;
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


  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR
   * call it in your code!
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
    panel1.add(panel2,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    final JPanel panel3 = new JPanel();
    panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel2.add(panel3,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    DATABASELabel = new JLabel();
    DATABASELabel.setBackground(new Color(-12508876));
    DATABASELabel.setForeground(new Color(-11682923));
    DATABASELabel.setHorizontalAlignment(0);
    DATABASELabel.setText("DATABASE");
    panel3.add(DATABASELabel,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 1, false));
    final JPanel panel4 = new JPanel();
    panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel2.add(panel4,
        new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    CSVLabel = new JLabel();
    CSVLabel.setBackground(new Color(-12508876));
    CSVLabel.setForeground(new Color(-11682923));
    CSVLabel.setHorizontalAlignment(0);
    CSVLabel.setText("CSV");
    panel4.add(CSVLabel,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    final JPanel panel5 = new JPanel();
    panel5.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel5,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    final JPanel panel6 = new JPanel();
    panel6.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1, true, true));
    panel5.add(panel6,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    questionsToCSVButton = new JButton();
    questionsToCSVButton.setText("Questions To CSV");
    panel6.add(questionsToCSVButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    teamsToCSVButton = new JButton();
    teamsToCSVButton.setText("Teams To CSV");
    panel6.add(teamsToCSVButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    schoolsToCSVButton = new JButton();
    schoolsToCSVButton.setText("Schools To CSV");
    panel6.add(schoolsToCSVButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    oneToFourteenCSVButton = new JButton();
    oneToFourteenCSVButton.setEnabled(true);
    oneToFourteenCSVButton.setText("Q1-Q14 to CSV");
    panel6.add(oneToFourteenCSVButton,
        new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel7 = new JPanel();
    panel7.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1, true, true));
    panel5.add(panel7,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    questionsToDatabaseButton = new JButton();
    questionsToDatabaseButton.setText("Questions To Database");
    panel7.add(questionsToDatabaseButton,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    teamsToDatabaseButton = new JButton();
    teamsToDatabaseButton.setText("Teams To Database");
    panel7.add(teamsToDatabaseButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    schoolsToDatabaseButton = new JButton();
    schoolsToDatabaseButton.setText("Schools To Database");
    panel7.add(schoolsToDatabaseButton,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    exitButton = new JButton();
    exitButton.setText("Exit");
    panel7.add(exitButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JPanel panel8 = new JPanel();
    panel8.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel8,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    CSVInputFileButton = new JButton();
    CSVInputFileButton.setText("Choose CSV File");
    panel8.add(CSVInputFileButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    loginDatabaseButton = new JButton();
    loginDatabaseButton.setText("Login Database");
    panel8.add(loginDatabaseButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return panel1;
  }

}
