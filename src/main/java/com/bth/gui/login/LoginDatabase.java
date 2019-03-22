package com.bth.gui.login;

import com.bth.gui.controller.DatabaseLoginUser;
import com.bth.gui.controller.GUIController;
import com.bth.io.database.mongodb.mongodbconnector.MongoDBConnection;
import com.bth.io.database.sql.sqlconnector.MySqlConnection;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LoginDatabase {

  public JPanel primaryPanel;
  public JButton exitButton;
  public JButton confirmButton;
  public JPanel exitConfirmPanel;
  public JTextArea sqlconnectorStringTextField;
  public JTextField UserName;
  public JComboBox DBTypeComboBox;
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
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(false);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);

    // DEV purpose.
    test();

    user = null;

    confirmButton.addActionListener(new ConfirmButtonListener());
    exitButton.addActionListener(e -> frame.dispose());
  }

  private void test() {
    this.mdbschoolCollectionNameTextField.setText("SchoolStatistics");
    this.mdbteamsCollectionNameTextField.setText("TeamStatistics");
    this.mdbquestionsNameTextField.setText("QuestionStatistics");
    this.mongoDbConnectStringTextField.setText(
        "mongodb+srv://edwin-carlsson:Edwin98@examanalysiscluster-hsaye.mongodb.net/test?retryWrites=true");
    this.mongoDatabaseNameTextField.setText("ExamAnalysisDatabase");
    this.UserName.setText("edwin-carlsson");
    this.Password.setText("Edwin98");

    this.sqlconnectorStringTextField.setText("jdbc:mysql://localhost:8889/stats_exams");
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

  // Local class for getting DBObject
  class ConfirmButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      String userName = UserName.getText();
      char[] password = Password.getPassword();

      String choice = (String) DBTypeComboBox.getItemAt(DBTypeComboBox.getSelectedIndex());

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
        user = new DatabaseLoginUser(userName, password, choice, sqlConnectorName, sqlDatabaseName,
            schoolTable, teamTable, questionTable, mongoConnectorName, mongoDatabaseName,
            schoolCollection, teamCollection, questionCollection);
        if (choice.equals("MySQL")) {
          mySqlConnection = new MySqlConnection(user);
          GUIController.setConnection(mySqlConnection);
          GUIController.dbChoice = true;
        } else {
          mongoDBConnection = new MongoDBConnection(user);
          GUIController.setConnection(mongoDBConnection);
          GUIController.dbChoice = false;
        }
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      frame.dispose();
    }
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
}
