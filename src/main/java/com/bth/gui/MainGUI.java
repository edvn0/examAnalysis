package com.bth.gui;

import com.bth.analysis.ExamAnalysis;
import com.bth.analysis.stats.StatsSchool;
import com.bth.analysis.stats.StatsTeam;
import com.bth.analysis.stats.helperobjects.RoundOffStatsQuestion;
import com.bth.exams.ExamSchool;
import com.bth.gui.controller.GUIController;
import com.bth.gui.csvchooser.CsvDirectoryChoice;
import com.bth.gui.examdirectorygui.ChooseInputFileFrame;
import com.bth.gui.login.LoginDatabase;
import com.bth.io.ExamOutput;
import java.awt.Component;
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


public class MainGUI {

  private static File file;
  private JPanel panel1;
  private JLabel DATABASELabel;
  private JLabel CSVLabel;
  private JButton questionsToDatabaseButton;
  private JButton teamsToDatabaseButton;
  private JButton schoolsToDatabaseButton;
  private JButton exitButton;
  private JButton questionsToCSVButton;
  private JButton teamsToCSVButton;
  private JButton schoolsToCSVButton;
  private JButton oneToFourteenCSVButton;
  private JButton CSVInputFileButton;
  private JButton loginDatabaseButton;
  private JButton insertEverythingButton;
  private JPanel databasePanel;
  private JPanel dbPanel;
  private JPanel csvPanel;

  private GUIController controller;
  private static String dir = "/Users/edwincarlsson/Documents/"
      + "Programmering/exam_Analysis/src/main/"
      + "resources/data/csvfiles/Deltävlingstentamen_2019_03_21.csv";
  private ExamAnalysis examAnalysis;

  private List<RoundOffStatsQuestion> questionsStats;
  private List<StatsSchool> statsSchools;
  private List<StatsTeam> statsTeams;
  private ExamSchool[] exams;

  private JFrame frame;

  public MainGUI() throws FileNotFoundException {
    controller = new GUIController();

    setup();
  }

  private void setup() throws FileNotFoundException {
    // Init gui
    frame = new JFrame("MainGUI");
    frame.setLocationByPlatform(true);
    frame.setContentPane(this.getPanel1());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);
    controller.append(this);
    controller.getMainGUI().getFrame().setVisible(false);
    // End init gui

    // Add all relevant components to a List to manage more easily.
    ArrayList<JComponent> list = addAllToList();
    // Disable prior to initialisation.
    controller.setEnabledForAll(list, false);
    controller.append(new LoginDatabase());
    controller.append(new ChooseInputFileFrame());
    controller.append(new CsvDirectoryChoice());

    // Invisible until resource directory has been chosen.
    controller.getCsvDirectoryChoice().fileChooser1.setVisible(false);

    // Init all the JButton listeners
    this.insertActionListener(list);

    getLoginDatabaseButton().addActionListener(e1 ->
        controller.getLoginDatabase().getFrame().setVisible(true));

    controller.getLoginDatabase().getConfirmButton().addActionListener(
        new DatabaseButtonListener());

    getCSVInputFileButton()
        .addActionListener(e -> controller.getCsvDirectoryChoice().init());

    getExitButton().addActionListener(e -> System.exit(0));

    if (file != null) {
      examAnalysis = new ExamAnalysis(file.getAbsolutePath());
    } else {
      examAnalysis = null;
    }
    if (examAnalysis != null) {
      examAnalysis.start();
      this.initArrays();
    }
    this.frame.setVisible(true);
  }

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

  public void setLoginDatabaseButton(JButton loginDatabaseButton) {
    this.loginDatabaseButton = loginDatabaseButton;
  }

  public JPanel getPanel1() {
    return panel1;
  }

  public void setPanel1(JPanel panel1) {
    this.panel1 = panel1;
  }

  public JLabel getDATABASELabel() {
    return DATABASELabel;
  }

  public void setDATABASELabel(JLabel DATABASELabel) {
    this.DATABASELabel = DATABASELabel;
  }

  public JLabel getCSVLabel() {
    return CSVLabel;
  }

  public void setCSVLabel(JLabel CSVLabel) {
    this.CSVLabel = CSVLabel;
  }

  public JButton getQuestionsToDatabaseButton() {
    return questionsToDatabaseButton;
  }

  public void setQuestionsToDatabaseButton(JButton questionsToDatabaseButton) {
    this.questionsToDatabaseButton = questionsToDatabaseButton;
  }

  public JButton getTeamsToDatabaseButton() {
    return teamsToDatabaseButton;
  }

  public void setTeamsToDatabaseButton(JButton teamsToDatabaseButton) {
    this.teamsToDatabaseButton = teamsToDatabaseButton;
  }

  public JButton getSchoolsToDatabaseButton() {
    return schoolsToDatabaseButton;
  }

  public void setSchoolsToDatabaseButton(JButton schoolsToDatabaseButton) {
    this.schoolsToDatabaseButton = schoolsToDatabaseButton;
  }

  public JButton getExitButton() {
    return exitButton;
  }

  public void setExitButton(JButton exitButton) {
    this.exitButton = exitButton;
  }

  public JButton getQuestionsToCSVButton() {
    return questionsToCSVButton;
  }

  public void setQuestionsToCSVButton(JButton questionsToCSVButton) {
    this.questionsToCSVButton = questionsToCSVButton;
  }

  public JButton getTeamsToCSVButton() {
    return teamsToCSVButton;
  }

  public void setTeamsToCSVButton(JButton teamsToCSVButton) {
    this.teamsToCSVButton = teamsToCSVButton;
  }

  public JButton getSchoolsToCSVButton() {
    return schoolsToCSVButton;
  }

  public void setSchoolsToCSVButton(JButton schoolsToCSVButton) {
    this.schoolsToCSVButton = schoolsToCSVButton;
  }

  public JButton getOneToFourteenCSVButton() {
    return oneToFourteenCSVButton;
  }

  public void setOneToFourteenCSVButton(JButton oneToFourteenCSVButton) {
    this.oneToFourteenCSVButton = oneToFourteenCSVButton;
  }

  public JButton getCSVInputFileButton() {
    return CSVInputFileButton;
  }

  public void setCSVInputFileButton(JButton CSVInputFileButton) {
    this.CSVInputFileButton = CSVInputFileButton;
  }

  private Component getFrame() {
    return this.frame;
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
                controller.getLoginDatabase().getMongoDBConnection().getUser().getQuestionTable());

            System.out.println("Inserted!");

          } else if (loggedIn[0]) {

            System.out.println("*-------------------------------------------------------*");
            System.out.println("Inserting " + e.getActionCommand() + " into both databases...");
            System.out.println("*-------------------------------------------------------*");

            controller.insertIntoMongoDatabase(null, null, questionsStats,
                null,
                null,
                controller.getLoginDatabase().getMongoDBConnection().getUser().getQuestionTable());

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
                controller.getLoginDatabase().getMongoDBConnection().getUser()
                    .getSchoolCollection(),
                null,
                null);

            System.out.println("Inserted!");
          } else if (loggedIn[0]) {
            System.out.println("*-------------------------------------------------------*");
            System.out.println("Inserting " + e.getActionCommand() + " into both databases...");
            System.out.println("*-------------------------------------------------------*");

            controller.insertIntoMongoDatabase(statsSchools, null, null,
                controller.getLoginDatabase().getMongoDBConnection().getUser()
                    .getSchoolCollection(),
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
                controller.getLoginDatabase().getMongoDBConnection().getUser().getTeamCollection(),
                null);

            System.out.println("Inserted!");
          } else if (loggedIn[0]) {
            System.out.println("*-------------------------------------------------------*");
            System.out.println("Inserting " + e.getActionCommand() + " into both databases...");
            System.out.println("*-------------------------------------------------------*");

            controller.insertIntoMongoDatabase(null, statsTeams, null,
                null,
                controller.getLoginDatabase().getMongoDBConnection().getUser().getTeamCollection(),
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

          long now = System.currentTimeMillis();
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
                    .getQuestionTable());
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
                    .getSchoolCollection(),
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
                    .getTeamCollection(),
                null);
            System.out.println("Inserted stats teams...");
            long now2 = System.currentTimeMillis();
            System.out.println("Total execution time was: " + (now2 - now) + "ms.");
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

  // Inner class for Database login
  class DatabaseButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      controller.setEnabledForAll(addAllToList(), true);
      getCSVInputFileButton().setEnabled(true);
    }

  }

  public static void setFileFromChooseDirectory(File file) {
    MainGUI.file = file;
  }
}
