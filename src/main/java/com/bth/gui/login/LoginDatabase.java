package com.bth.gui.login;

import com.bth.gui.controller.loginusers.DatabaseLoginUser;
import com.bth.gui.controller.GUIController;
import com.bth.gui.controller.loginusers.MongoDBUser;
import com.bth.gui.controller.loginusers.SQLLoginUser;
import com.bth.io.input.PropertiesReader;
import com.bth.io.output.database.mongodb.mongodbconnector.MongoDBConnection;
import com.bth.io.output.database.sql.sqlconnector.MySqlConnection;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LoginDatabase {

  private final ThreadLocal<PropertiesReader> reader = new ThreadLocal<>();
  public JPanel primaryPanel;
  public JButton exitButton;
  public JButton confirmButton;
  public JPanel exitConfirmPanel;
  public JTextArea sqlconnectorStringTextField;
  public JTextField UserName;
  public JComboBox<? extends String> DBTypeComboBox;
  public JPasswordField Password;
  public JTextField mdbschoolCollectionNameTextField;
  public JTextField mongoDatabaseNameTextField;
  public JPanel databasePanel;
  public JPanel databaseInputPanel;
  public JPanel mongoDBNamePanel;
  public JPanel mongoDBCollectionPanel;
  public JTextField mdbquestionsNameTextField;
  public JTextField mdbteamsCollectionNameTextField;
  public JTextField sqlschoolsTableNameTextField;
  public JTextField sqlquestionsTableNameTextField;
  public JTextField sqlteamsTableNameTextField;
  public JPanel mysqlTablePanel;
  public JLabel mySqlTableLable;
  public JLabel mongoDbDatabaseNameLable;
  public JLabel mongoDBCollectionNameLable;
  public JLabel mySQLJDBCStringLabel;
  public JTextField mySQLDatabaseNameTextField;
  public JLabel choiceOfDatabaseLabel;
  public JLabel mySQLDatabaseNameLabel;
  public JPanel mySQLDatabasePanel;
  public JPanel choiceDBPanel;
  public JTextArea mongoDbConnectStringTextField;
  public JLabel mongoDbDatabaseNameLabel;
  private JFrame frame;
  private DatabaseLoginUser user;
  private MongoDBConnection mongoDBConnection;
  private MySqlConnection mySqlConnection;

  public LoginDatabase() {
    frame = new JFrame("Login to Database");
    frame.setContentPane(this.primaryPanel);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.pack();
    frame.setVisible(false);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);

    // DEV!
    try {
      reader.set(new PropertiesReader(
          "/Users/edwincarlsson/Documents/Programmering/exam_Analysis/.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    test();
    // END DEV!

    user = null;

    confirmButton.addActionListener(new ConfirmButtonListener());
    exitButton.addActionListener(e -> frame.dispose());
  }

  private void test() {
    this.mdbschoolCollectionNameTextField.setText("SchoolStatistics");
    this.mdbteamsCollectionNameTextField.setText("TeamStatistics");
    this.mdbquestionsNameTextField.setText("QuestionStatistics");
    this.mongoDbConnectStringTextField
        .setText(reader.get().getProperty("connect.string.for.mongo"));
    this.mongoDatabaseNameTextField.setText("ExamAnalysisDatabase");
    this.UserName.setText("edwin-carlsson");
    this.Password.setText("Edwin98");

    this.sqlconnectorStringTextField.setText(reader.get().getProperty("connect.string.for.mysql"));
    this.mySQLDatabaseNameTextField.setText("stats_exams");
    this.sqlschoolsTableNameTextField.setText("school_statistics");
    this.sqlteamsTableNameTextField.setText("team_statistics");
    this.sqlquestionsTableNameTextField.setText("questions");
  }

  public Component getFrame() {
    return frame;
  }

  public MySqlConnection getMySqlConnection() {
    return mySqlConnection;
  }

  public MongoDBConnection getMongoDBConnection() {
    return mongoDBConnection;
  }

  public JButton getConfirmButton() {
    return this.confirmButton;
  }

  private String[] getConnectivityInformation() {
    return new String[]{
        sqlconnectorStringTextField.getText(),
        mySQLDatabaseNameTextField.getText(),
        sqlschoolsTableNameTextField.getText(),
        sqlteamsTableNameTextField.getText(),
        sqlquestionsTableNameTextField.getText(),
        mongoDbConnectStringTextField.getText(),
        mongoDatabaseNameTextField.getText(),
        mdbschoolCollectionNameTextField.getText(),
        mdbteamsCollectionNameTextField.getText(),
        mdbquestionsNameTextField.getText(),
    };
  }

  // Local class for getting DBObject
  class ConfirmButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      String userName = UserName.getText();
      char[] password = Password.getPassword();

      String choice = DBTypeComboBox.getItemAt(DBTypeComboBox.getSelectedIndex());

      String[] fullInfo = getConnectivityInformation();

      // MYSQL INFO
      // Connector Connection Name, Database name, schooltable, teamtable, questiontable.
      String sqlConnectorName = fullInfo[0];
      String sqlDatabaseName = fullInfo[1];
      String schoolTable = fullInfo[2];
      String teamTable = fullInfo[3];
      String questionTable = fullInfo[4];

      // MONGODB INFO
      // Connector MongoConnector Name, Database name, schoolCollection, teamCollection, questionCollection
      String mongoConnectorName = fullInfo[5];
      String mongoDatabaseName = fullInfo[6];
      String schoolCollection = fullInfo[7];
      String teamCollection = fullInfo[8];
      String questionCollection = fullInfo[9];

      try {
        if (choice.equals("MySQL")) {
          mySqlConnection = new MySqlConnection(
              new SQLLoginUser(userName, password, sqlDatabaseName, teamTable, schoolTable,
                  questionTable, sqlConnectorName));
          GUIController.setConnection(mySqlConnection);
        } else {
          mongoDBConnection = new MongoDBConnection(
              new MongoDBUser(userName, password, mongoDatabaseName, schoolCollection,
                  teamCollection, questionCollection, mongoConnectorName));
          GUIController.setConnection(mongoDBConnection);
        }
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      frame.dispose();
    }
  }
}
