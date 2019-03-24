package com.bth.gui;

import com.bth.analysis.Stats.StatsSchool;
import com.bth.analysis.Stats.StatsTeam;
import com.bth.analysis.Stats.helperobjects.RoundOffStatsQuestion;
import com.bth.exams.ExamSchool;
import com.bth.gui.controller.GUIController;
import com.bth.gui.csvchooser.CsvDirectoryChoice;
import com.bth.gui.examdirectorygui.ChooseDirectory;
import com.bth.gui.login.LoginDatabase;
import com.bth.io.ExamOutput;
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
  private JButton newPossibilityButton;

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

    System.out.println(schoolsToDatabaseButton.getPreferredSize());

    java.awt.EventQueue.invokeLater(ChooseDirectory::new);

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
}
