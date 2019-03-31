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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class MainGUI {

  private static File file;
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
  private JButton insertEverythingButton;

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

  public MainGUI() {
    controller = new GUIController();

    setup();
  }

  private void setup() {
    // Init gui
    frame = new JFrame("MainGUI");
    frame.setLocationByPlatform(true);
    frame.setContentPane(this.panel1);
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

    // Invis until resource directory has been chosen.
    controller.getCsvDirectoryChoice().fileChooser1.setVisible(false);

    // Init all the Jbutton listeners
    this.insertActionListener(list);

    loginDatabaseButton.addActionListener(e1 ->
        controller.getLoginDatabase().getFrame().setVisible(true));

    controller.getLoginDatabase().getConfirmButton().addActionListener(
        new DatabaseButtonListener());

    CSVInputFileButton
        .addActionListener(e -> {
          controller.getCsvDirectoryChoice().init();
        });

    exitButton.addActionListener(e -> System.exit(0));

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

  private boolean resetExamAnalysis(boolean isExamNull) {
    JOptionPane.showMessageDialog(controller.getChooseInputFileFrame().mainPanel,
        "You need to input a file for this analyser to work. Choose the correct csv file.");
    if (isExamNull) {
      this.controller.setChooseInputFileFrame(null);
      this.controller.append(new ChooseInputFileFrame());
    }
    return isExamNull;
  }

  private void initArrays() {
    this.questionsStats = examAnalysis.getQuestionsStats();
    this.statsSchools = examAnalysis.getStatsSchools();
    this.statsTeams = examAnalysis.getStatsTeams();
    this.exams = examAnalysis.getExamSchools();
  }

  private Component getFrame() {
    return this.frame;
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
    temp.add(insertEverythingButton);
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
          ExamOutput.printQuestionsToCSV(exams, examAnalysis.getExamTeams());
          System.out.println("Printed!");
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
        case "insert everything":
          long now = System.currentTimeMillis();
          System.out.println(
              "Inserting school, team and questions info into both sql and mongodb databases...");
          try {
            System.out.println("Inserting questions into SQL...");
            controller.insertIntoMySQLDatabase(null,
                null, questionsStats,
                controller.getLoginDatabase().getMySqlConnection().getUser().getQuestionTable());
            System.out.println("Inserted questions...");
            System.out.println("Inserting questions into Mongo...");

            controller.insertIntoMongoDatabase(null, null, questionsStats,
                null,
                null,
                controller.getLoginDatabase().getMongoDBConnection().getUser().getQuestionTable());
            System.out.println("Inserted questions...");
            System.out.println("Inserting school stats into SQL...");

            controller.insertIntoMySQLDatabase(null,
                statsSchools, null,
                controller.getLoginDatabase().getMySqlConnection().getUser().getSchoolTable());
            System.out.println("Inserted school stats...");
            System.out.println("Inserting school stats into Mongo...");

            controller.insertIntoMongoDatabase(statsSchools, null, null,
                controller.getLoginDatabase().getMongoDBConnection().getUser()
                    .getSchoolCollection(),
                null,
                null);
            System.out.println("Inserted school stats...");
            System.out.println("Inserting stats teams into SQL...");

            controller.insertIntoMySQLDatabase(statsTeams,
                null, null,
                controller.getLoginDatabase().getMySqlConnection().getUser().getTeamTable());
            System.out.println("Inserted stats teams...");
            System.out.println("Inserting stats teams into Mongo...");

            controller.insertIntoMongoDatabase(null, statsTeams, null,
                null,
                controller.getLoginDatabase().getMongoDBConnection().getUser().getTeamCollection(),
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
      CSVInputFileButton.setEnabled(true);
    }
  }

  public static void setFileFromChooseDirectory(File file) {
    System.out.println(file);
    MainGUI.file = file;
  }
}
