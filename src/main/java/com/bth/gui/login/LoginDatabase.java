package com.bth.gui.login;

import com.bth.gui.controller.DatabaseLoginUser;
import com.bth.gui.controller.GUIController;
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
  public JFrame frame;

  private DatabaseLoginUser user;

  private Connection connection;
  private MongoCollection<BasicDBObject> objectsInDb;

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
    connection = null;
    objectsInDb = null;

    confirmButton.addActionListener(new ConfirmButtonListener());
    exitButton.addActionListener(e -> {
      frame.dispose();
    });
  }

  private void test() {
    this.mdbschoolCollectionNameTextField.setText("SchoolStatistics");
    this.mdbteamsCollectionNameTextField.setText("TeamStatistics");
    this.mdbquestionsNameTextField.setText("QuestionStatistics");
    this.mongoDbConnectStringTextField.setText("mongodb+srv://edwin-carlsson:Edwin98@examanalysiscluster-hsaye.mongodb.net/test?retryWrites=true");
    this.mongoDatabaseNameTextField.setText("ExamAnalysisDatabase");
    this.UserName.setText("edwin-carlsson");
    this.Password.setText("Edwin98");

    this.sqlconnectorStringTextField.setText("jdbc:mysql://localhost:8889/stats_exams");
    this.mySQLDatabaseNameTextField.setText("stats_exams");
    this.sqlschoolsTableNameTextField.setText("school_statistics");
    this.sqlteamsTableNameTextField.setText("team_statistics");
    this.sqlquestionsTableNameTextField.setText("questions");
  }

  public Connection getConnection() {
    return connection;
  }

  public MongoCollection<BasicDBObject> getObjectsInDb() {
    return objectsInDb;
  }


  // Local class for getting DBObject
  class ConfirmButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String userName = UserName.getText();
      char[] password = Password.getPassword();

      String choice = (String) DBTypeComboBox.getItemAt(DBTypeComboBox.getSelectedIndex());

      // MYSQL INFO
      // Connector Connection Name, Database name, schooltable, teamtable, questiontable.
      String sqlConnectorName = sqlconnectorStringTextField.getText();
      String sqlDatabaseName = mySQLDatabaseNameTextField.getText();
      String schoolTable = sqlschoolsTableNameTextField.getText();
      String teamTable = sqlteamsTableNameTextField.getText();
      String questionTable = sqlquestionsTableNameTextField.getText();

      // MONGODB INFO
      // Connector MongoConnector Name, Database name, schoolCollection, teamCollection, questionCollection
      String mongoConnectorName = mongoDbConnectStringTextField.getText();
      String mongoDatabaseName = mongoDatabaseNameTextField.getText();
      String schoolCollection = mdbschoolCollectionNameTextField.getText();
      String teamCollection = mdbteamsCollectionNameTextField.getText();
      String questionCollection = mdbquestionsNameTextField.getText();

      try {
        user = new DatabaseLoginUser(userName, password, choice, sqlConnectorName, sqlDatabaseName, schoolTable, teamTable, questionTable, mongoConnectorName, mongoDatabaseName, schoolCollection, teamCollection, questionCollection);
        //user = new DatabaseLoginUser(mongo, choice, sqlConnectorName, userName, password);
        if (choice.equals("MySQL")) {
          SQLController.setDatabaseLoginUser(user);
          connection = SQLConnector.connectToDatabase();
          GUIController.dbChoice = true;
        } else {
          MongoDBController.setDatabaseLoginUser(user);
          MongoDBConnector.connectToMongoDB();
          GUIController.dbChoice = false;
        }
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      frame.dispose();
    }
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
    primaryPanel = new JPanel();
    primaryPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
    primaryPanel.setMaximumSize(new Dimension(1040, 550));
    primaryPanel.setMinimumSize(new Dimension(1040, 550));
    primaryPanel.setOpaque(false);
    primaryPanel.setPreferredSize(new Dimension(1040, 550));
    exitConfirmPanel = new JPanel();
    exitConfirmPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    primaryPanel.add(exitConfirmPanel, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    exitButton = new JButton();
    exitButton.setText("Exit");
    exitConfirmPanel.add(exitButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    confirmButton = new JButton();
    confirmButton.setText("Confirm");
    exitConfirmPanel.add(confirmButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    databasePanel = new JPanel();
    databasePanel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
    primaryPanel.add(databasePanel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    UserName = new JTextField();
    UserName.setHorizontalAlignment(0);
    UserName.setText("Username for DB");
    databasePanel.add(UserName, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    Password = new JPasswordField();
    Password.setHorizontalAlignment(0);
    Password.setText("Password");
    databasePanel.add(Password, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    databaseInputPanel = new JPanel();
    databaseInputPanel.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
    databasePanel.add(databaseInputPanel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    mongoDBNamePanel = new JPanel();
    mongoDBNamePanel.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
    mongoDBNamePanel.setBackground(new Color(-11682923));
    databaseInputPanel.add(mongoDBNamePanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    mongoDatabaseNameTextField = new JTextField();
    mongoDatabaseNameTextField.setBackground(new Color(-12508876));
    mongoDatabaseNameTextField.setForeground(new Color(-1));
    mongoDatabaseNameTextField.setHorizontalAlignment(0);
    mongoDatabaseNameTextField.setInheritsPopupMenu(false);
    mongoDatabaseNameTextField.setText("Mongo Database Name");
    mongoDBNamePanel.add(mongoDatabaseNameTextField, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    mongoDbDatabaseNameLable = new JLabel();
    Font mongoDbDatabaseNameLableFont = this.$$$getFont$$$("Monaco", -1, 18, mongoDbDatabaseNameLable.getFont());
    if (mongoDbDatabaseNameLableFont != null) mongoDbDatabaseNameLable.setFont(mongoDbDatabaseNameLableFont);
    mongoDbDatabaseNameLable.setHorizontalAlignment(0);
    mongoDbDatabaseNameLable.setHorizontalTextPosition(0);
    mongoDbDatabaseNameLable.setText("MongoDB Connect String");
    mongoDBNamePanel.add(mongoDbDatabaseNameLable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    mongoDbConnectStringTextField = new JTextArea();
    mongoDbConnectStringTextField.setBackground(new Color(-12508876));
    Font mongoDbConnectStringTextFieldFont = this.$$$getFont$$$("Monaco", -1, 14, mongoDbConnectStringTextField.getFont());
    if (mongoDbConnectStringTextFieldFont != null)
      mongoDbConnectStringTextField.setFont(mongoDbConnectStringTextFieldFont);
    mongoDbConnectStringTextField.setForeground(new Color(-1));
    mongoDbConnectStringTextField.setLineWrap(true);
    mongoDbConnectStringTextField.setText("mongodb+srv://<user>:<password>@<cluster>.mongodb.net/test?retryWrites=true");
    mongoDbConnectStringTextField.setWrapStyleWord(true);
    mongoDBNamePanel.add(mongoDbConnectStringTextField, new GridConstraints(1, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(150, 150), new Dimension(150, 150), new Dimension(150, 200), 0, false));
    mongoDbDatabaseNameLabel = new JLabel();
    Font mongoDbDatabaseNameLabelFont = this.$$$getFont$$$("Monaco", -1, 18, mongoDbDatabaseNameLabel.getFont());
    if (mongoDbDatabaseNameLabelFont != null) mongoDbDatabaseNameLabel.setFont(mongoDbDatabaseNameLabelFont);
    mongoDbDatabaseNameLabel.setHorizontalAlignment(0);
    mongoDbDatabaseNameLabel.setHorizontalTextPosition(0);
    mongoDbDatabaseNameLabel.setText("MongoDB Database Name");
    mongoDBNamePanel.add(mongoDbDatabaseNameLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    mongoDBCollectionPanel = new JPanel();
    mongoDBCollectionPanel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
    mongoDBCollectionPanel.setBackground(new Color(-11682923));
    databaseInputPanel.add(mongoDBCollectionPanel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    mdbschoolCollectionNameTextField = new JTextField();
    mdbschoolCollectionNameTextField.setBackground(new Color(-12508876));
    mdbschoolCollectionNameTextField.setDisabledTextColor(new Color(-12508876));
    mdbschoolCollectionNameTextField.setForeground(new Color(-1));
    mdbschoolCollectionNameTextField.setHorizontalAlignment(0);
    mdbschoolCollectionNameTextField.setText("Schools Collection Name");
    mongoDBCollectionPanel.add(mdbschoolCollectionNameTextField, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    mdbquestionsNameTextField = new JTextField();
    mdbquestionsNameTextField.setBackground(new Color(-12508876));
    mdbquestionsNameTextField.setForeground(new Color(-1));
    mdbquestionsNameTextField.setHorizontalAlignment(0);
    mdbquestionsNameTextField.setText("Questions Collection Name");
    mongoDBCollectionPanel.add(mdbquestionsNameTextField, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    mdbteamsCollectionNameTextField = new JTextField();
    mdbteamsCollectionNameTextField.setBackground(new Color(-12508876));
    mdbteamsCollectionNameTextField.setForeground(new Color(-1));
    mdbteamsCollectionNameTextField.setHorizontalAlignment(0);
    mdbteamsCollectionNameTextField.setText("Teams Collection Name");
    mongoDBCollectionPanel.add(mdbteamsCollectionNameTextField, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    mongoDBCollectionNameLable = new JLabel();
    Font mongoDBCollectionNameLableFont = this.$$$getFont$$$("Monaco", -1, 18, mongoDBCollectionNameLable.getFont());
    if (mongoDBCollectionNameLableFont != null) mongoDBCollectionNameLable.setFont(mongoDBCollectionNameLableFont);
    mongoDBCollectionNameLable.setHorizontalAlignment(0);
    mongoDBCollectionNameLable.setHorizontalTextPosition(0);
    mongoDBCollectionNameLable.setText("MongoDB Collection Names");
    mongoDBCollectionPanel.add(mongoDBCollectionNameLable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    mysqlTablePanel = new JPanel();
    mysqlTablePanel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
    mysqlTablePanel.setBackground(new Color(-11682923));
    databaseInputPanel.add(mysqlTablePanel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    sqlschoolsTableNameTextField = new JTextField();
    sqlschoolsTableNameTextField.setBackground(new Color(-12508876));
    sqlschoolsTableNameTextField.setForeground(new Color(-1));
    sqlschoolsTableNameTextField.setHorizontalAlignment(0);
    sqlschoolsTableNameTextField.setText("Schools Table Name");
    mysqlTablePanel.add(sqlschoolsTableNameTextField, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    sqlquestionsTableNameTextField = new JTextField();
    sqlquestionsTableNameTextField.setBackground(new Color(-12508876));
    sqlquestionsTableNameTextField.setForeground(new Color(-1));
    sqlquestionsTableNameTextField.setHorizontalAlignment(0);
    sqlquestionsTableNameTextField.setText("Questions Table Name");
    mysqlTablePanel.add(sqlquestionsTableNameTextField, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    sqlteamsTableNameTextField = new JTextField();
    sqlteamsTableNameTextField.setBackground(new Color(-12508876));
    sqlteamsTableNameTextField.setForeground(new Color(-1));
    sqlteamsTableNameTextField.setHorizontalAlignment(0);
    sqlteamsTableNameTextField.setText("Teams Table Name");
    mysqlTablePanel.add(sqlteamsTableNameTextField, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    mySqlTableLable = new JLabel();
    Font mySqlTableLableFont = this.$$$getFont$$$("Monaco", -1, 18, mySqlTableLable.getFont());
    if (mySqlTableLableFont != null) mySqlTableLable.setFont(mySqlTableLableFont);
    mySqlTableLable.setHorizontalAlignment(0);
    mySqlTableLable.setHorizontalTextPosition(0);
    mySqlTableLable.setText("MySQL Table Names");
    mysqlTablePanel.add(mySqlTableLable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    mySQLDatabasePanel = new JPanel();
    mySQLDatabasePanel.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
    mySQLDatabasePanel.setBackground(new Color(-11682923));
    databaseInputPanel.add(mySQLDatabasePanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    mySQLJDBCStringLabel = new JLabel();
    Font mySQLJDBCStringLabelFont = this.$$$getFont$$$("Monaco", -1, 18, mySQLJDBCStringLabel.getFont());
    if (mySQLJDBCStringLabelFont != null) mySQLJDBCStringLabel.setFont(mySQLJDBCStringLabelFont);
    mySQLJDBCStringLabel.setHorizontalAlignment(0);
    mySQLJDBCStringLabel.setHorizontalTextPosition(0);
    mySQLJDBCStringLabel.setText("MySQL JDBC String");
    mySQLDatabasePanel.add(mySQLJDBCStringLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    mySQLDatabaseNameLabel = new JLabel();
    Font mySQLDatabaseNameLabelFont = this.$$$getFont$$$("Monaco", -1, 18, mySQLDatabaseNameLabel.getFont());
    if (mySQLDatabaseNameLabelFont != null) mySQLDatabaseNameLabel.setFont(mySQLDatabaseNameLabelFont);
    mySQLDatabaseNameLabel.setHorizontalAlignment(0);
    mySQLDatabaseNameLabel.setHorizontalTextPosition(0);
    mySQLDatabaseNameLabel.setText("MySQL Database Name");
    mySQLDatabasePanel.add(mySQLDatabaseNameLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    mySQLDatabaseNameTextField = new JTextField();
    mySQLDatabaseNameTextField.setBackground(new Color(-12508876));
    mySQLDatabaseNameTextField.setForeground(new Color(-1));
    mySQLDatabaseNameTextField.setHorizontalAlignment(0);
    mySQLDatabaseNameTextField.setText("MySQL Database Name");
    mySQLDatabasePanel.add(mySQLDatabaseNameTextField, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    sqlconnectorStringTextField = new JTextArea();
    sqlconnectorStringTextField.setBackground(new Color(-12508876));
    Font sqlconnectorStringTextFieldFont = this.$$$getFont$$$("Monaco", -1, 14, sqlconnectorStringTextField.getFont());
    if (sqlconnectorStringTextFieldFont != null) sqlconnectorStringTextField.setFont(sqlconnectorStringTextFieldFont);
    sqlconnectorStringTextField.setForeground(new Color(-1));
    sqlconnectorStringTextField.setLineWrap(true);
    sqlconnectorStringTextField.setText("jdbc:mysql://<localhost:port>/<db_name>");
    sqlconnectorStringTextField.setWrapStyleWord(true);
    mySQLDatabasePanel.add(sqlconnectorStringTextField, new GridConstraints(1, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(150, 150), new Dimension(150, 150), new Dimension(150, 200), 0, false));
    choiceDBPanel = new JPanel();
    choiceDBPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    databasePanel.add(choiceDBPanel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    DBTypeComboBox = new JComboBox();
    final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
    defaultComboBoxModel1.addElement("MongoDB");
    defaultComboBoxModel1.addElement("MySQL");
    DBTypeComboBox.setModel(defaultComboBoxModel1);
    choiceDBPanel.add(DBTypeComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    choiceOfDatabaseLabel = new JLabel();
    Font choiceOfDatabaseLabelFont = this.$$$getFont$$$("Monaco", -1, 18, choiceOfDatabaseLabel.getFont());
    if (choiceOfDatabaseLabelFont != null) choiceOfDatabaseLabel.setFont(choiceOfDatabaseLabelFont);
    choiceOfDatabaseLabel.setHorizontalAlignment(0);
    choiceOfDatabaseLabel.setHorizontalTextPosition(0);
    choiceOfDatabaseLabel.setText("Choice of Database");
    choiceDBPanel.add(choiceOfDatabaseLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
    if (currentFont == null) return null;
    String resultName;
    if (fontName == null) {
      resultName = currentFont.getName();
    } else {
      Font testFont = new Font(fontName, Font.PLAIN, 10);
      if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
        resultName = fontName;
      } else {
        resultName = currentFont.getName();
      }
    }
    return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return primaryPanel;
  }
}
