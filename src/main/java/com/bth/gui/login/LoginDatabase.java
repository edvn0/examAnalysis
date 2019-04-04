package com.bth.gui.login;

import com.bth.gui.controller.loginusers.DatabaseLoginUser;
import com.bth.gui.controller.GuiController;
import com.bth.gui.controller.loginusers.MongoDBUser;
import com.bth.gui.controller.loginusers.SQLLoginUser;
import com.bth.io.input.PropertiesReader;
import com.bth.io.output.database.mongodb.mongodbconnector.MongoDBConnection;
import com.bth.io.output.database.sql.sqlconnector.MySqlConnection;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
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
    primaryPanel = new JPanel();
    primaryPanel.setLayout(new GridLayoutManager(2, 1, new Insets(8, 8, 8, 8), -1, -1));
    primaryPanel.setBackground(new Color(-2039585));
    primaryPanel.setForeground(new Color(-2039585));
    primaryPanel.setMaximumSize(new Dimension(1040, 550));
    primaryPanel.setMinimumSize(new Dimension(1040, 550));
    primaryPanel.setOpaque(false);
    primaryPanel.setPreferredSize(new Dimension(1040, 550));
    exitConfirmPanel = new JPanel();
    exitConfirmPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    exitConfirmPanel.setBackground(new Color(-2039585));
    exitConfirmPanel.setOpaque(true);
    primaryPanel.add(exitConfirmPanel,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    exitConfirmPanel.setBorder(BorderFactory
        .createTitledBorder(BorderFactory.createLineBorder(new Color(-4973171)), null));
    exitButton = new JButton();
    exitButton.setBackground(new Color(-2039585));
    Font exitButtonFont = this.$$$getFont$$$("TheSans", -1, 14, exitButton.getFont());
    if (exitButtonFont != null) {
      exitButton.setFont(exitButtonFont);
    }
    exitButton.setForeground(new Color(-4973171));
    exitButton.setHorizontalTextPosition(0);
    exitButton.setOpaque(true);
    exitButton.setText("Exit");
    exitConfirmPanel.add(exitButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    confirmButton = new JButton();
    confirmButton.setBackground(new Color(-2039585));
    Font confirmButtonFont = this.$$$getFont$$$("TheSans", -1, 14, confirmButton.getFont());
    if (confirmButtonFont != null) {
      confirmButton.setFont(confirmButtonFont);
    }
    confirmButton.setForeground(new Color(-4973171));
    confirmButton.setHorizontalTextPosition(0);
    confirmButton.setOpaque(true);
    confirmButton.setText("Confirm");
    exitConfirmPanel.add(confirmButton,
        new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    databasePanel = new JPanel();
    databasePanel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
    databasePanel.setBackground(new Color(-2039585));
    primaryPanel.add(databasePanel,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    databasePanel.setBorder(BorderFactory
        .createTitledBorder(BorderFactory.createLineBorder(new Color(-4973171)), null));
    UserName = new JTextField();
    UserName.setBackground(new Color(-2039585));
    Font UserNameFont = this.$$$getFont$$$("TheSans", -1, 14, UserName.getFont());
    if (UserNameFont != null) {
      UserName.setFont(UserNameFont);
    }
    UserName.setForeground(new Color(-4973171));
    UserName.setHorizontalAlignment(0);
    UserName.setText("Username for DB");
    databasePanel.add(UserName, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    Password = new JPasswordField();
    Password.setBackground(new Color(-2039585));
    Font PasswordFont = this.$$$getFont$$$("TheSans", -1, 14, Password.getFont());
    if (PasswordFont != null) {
      Password.setFont(PasswordFont);
    }
    Password.setForeground(new Color(-4973171));
    Password.setHorizontalAlignment(0);
    Password.setText("Password");
    databasePanel.add(Password, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    databaseInputPanel = new JPanel();
    databaseInputPanel.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
    databaseInputPanel.setBackground(new Color(-2039585));
    databasePanel.add(databaseInputPanel,
        new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    mongoDBNamePanel = new JPanel();
    mongoDBNamePanel.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
    mongoDBNamePanel.setBackground(new Color(-2039585));
    databaseInputPanel.add(mongoDBNamePanel,
        new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    mongoDBNamePanel.setBorder(BorderFactory
        .createTitledBorder(BorderFactory.createLineBorder(new Color(-4973171)), null));
    mongoDatabaseNameTextField = new JTextField();
    mongoDatabaseNameTextField.setBackground(new Color(-2039585));
    Font mongoDatabaseNameTextFieldFont = this
        .$$$getFont$$$("TheSans", -1, 14, mongoDatabaseNameTextField.getFont());
    if (mongoDatabaseNameTextFieldFont != null) {
      mongoDatabaseNameTextField.setFont(mongoDatabaseNameTextFieldFont);
    }
    mongoDatabaseNameTextField.setForeground(new Color(-4973171));
    mongoDatabaseNameTextField.setHorizontalAlignment(0);
    mongoDatabaseNameTextField.setInheritsPopupMenu(false);
    mongoDatabaseNameTextField.setText("Mongo Database Name");
    mongoDBNamePanel.add(mongoDatabaseNameTextField,
        new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    mongoDbDatabaseNameLable = new JLabel();
    mongoDbDatabaseNameLable.setBackground(new Color(-2039585));
    Font mongoDbDatabaseNameLableFont = this
        .$$$getFont$$$("TheSans", Font.BOLD, 18, mongoDbDatabaseNameLable.getFont());
    if (mongoDbDatabaseNameLableFont != null) {
      mongoDbDatabaseNameLable.setFont(mongoDbDatabaseNameLableFont);
    }
    mongoDbDatabaseNameLable.setForeground(new Color(-4973171));
    mongoDbDatabaseNameLable.setHorizontalAlignment(0);
    mongoDbDatabaseNameLable.setHorizontalTextPosition(0);
    mongoDbDatabaseNameLable.setText("MongoDB Connect String");
    mongoDBNamePanel.add(mongoDbDatabaseNameLable,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    mongoDbConnectStringTextField = new JTextArea();
    mongoDbConnectStringTextField.setBackground(new Color(-4973171));
    Font mongoDbConnectStringTextFieldFont = this
        .$$$getFont$$$("TheSans", -1, -1, mongoDbConnectStringTextField.getFont());
    if (mongoDbConnectStringTextFieldFont != null) {
      mongoDbConnectStringTextField.setFont(mongoDbConnectStringTextFieldFont);
    }
    mongoDbConnectStringTextField.setForeground(new Color(-2039585));
    mongoDbConnectStringTextField.setLineWrap(true);
    mongoDbConnectStringTextField
        .setText("mongodb+srv://<user>:<password>@<cluster>.mongodb.net/test?retryWrites=true");
    mongoDbConnectStringTextField.setWrapStyleWord(true);
    mongoDBNamePanel.add(mongoDbConnectStringTextField,
        new GridConstraints(1, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW,
            new Dimension(150, 150), new Dimension(150, 150), new Dimension(150, 200), 0, false));
    mongoDbDatabaseNameLabel = new JLabel();
    mongoDbDatabaseNameLabel.setBackground(new Color(-2039585));
    Font mongoDbDatabaseNameLabelFont = this
        .$$$getFont$$$("TheSans", Font.BOLD, 18, mongoDbDatabaseNameLabel.getFont());
    if (mongoDbDatabaseNameLabelFont != null) {
      mongoDbDatabaseNameLabel.setFont(mongoDbDatabaseNameLabelFont);
    }
    mongoDbDatabaseNameLabel.setForeground(new Color(-4973171));
    mongoDbDatabaseNameLabel.setHorizontalAlignment(0);
    mongoDbDatabaseNameLabel.setHorizontalTextPosition(0);
    mongoDbDatabaseNameLabel.setText("MongoDB Database Name");
    mongoDBNamePanel.add(mongoDbDatabaseNameLabel,
        new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    mongoDBCollectionPanel = new JPanel();
    mongoDBCollectionPanel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
    mongoDBCollectionPanel.setBackground(new Color(-2039585));
    databaseInputPanel.add(mongoDBCollectionPanel,
        new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    mongoDBCollectionPanel.setBorder(BorderFactory
        .createTitledBorder(BorderFactory.createLineBorder(new Color(-4973171)), null));
    mdbschoolCollectionNameTextField = new JTextField();
    mdbschoolCollectionNameTextField.setBackground(new Color(-2039585));
    mdbschoolCollectionNameTextField.setDisabledTextColor(new Color(-12508876));
    Font mdbschoolCollectionNameTextFieldFont = this
        .$$$getFont$$$("TheSans", -1, 14, mdbschoolCollectionNameTextField.getFont());
    if (mdbschoolCollectionNameTextFieldFont != null) {
      mdbschoolCollectionNameTextField.setFont(mdbschoolCollectionNameTextFieldFont);
    }
    mdbschoolCollectionNameTextField.setForeground(new Color(-4973171));
    mdbschoolCollectionNameTextField.setHorizontalAlignment(0);
    mdbschoolCollectionNameTextField.setText("Schools Collection Name");
    mongoDBCollectionPanel.add(mdbschoolCollectionNameTextField,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    mdbquestionsNameTextField = new JTextField();
    mdbquestionsNameTextField.setBackground(new Color(-2039585));
    Font mdbquestionsNameTextFieldFont = this
        .$$$getFont$$$("TheSans", -1, 14, mdbquestionsNameTextField.getFont());
    if (mdbquestionsNameTextFieldFont != null) {
      mdbquestionsNameTextField.setFont(mdbquestionsNameTextFieldFont);
    }
    mdbquestionsNameTextField.setForeground(new Color(-4973171));
    mdbquestionsNameTextField.setHorizontalAlignment(0);
    mdbquestionsNameTextField.setText("Questions Collection Name");
    mongoDBCollectionPanel.add(mdbquestionsNameTextField,
        new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    mdbteamsCollectionNameTextField = new JTextField();
    mdbteamsCollectionNameTextField.setBackground(new Color(-2039585));
    Font mdbteamsCollectionNameTextFieldFont = this
        .$$$getFont$$$("TheSans", -1, 14, mdbteamsCollectionNameTextField.getFont());
    if (mdbteamsCollectionNameTextFieldFont != null) {
      mdbteamsCollectionNameTextField.setFont(mdbteamsCollectionNameTextFieldFont);
    }
    mdbteamsCollectionNameTextField.setForeground(new Color(-4973171));
    mdbteamsCollectionNameTextField.setHorizontalAlignment(0);
    mdbteamsCollectionNameTextField.setText("Teams Collection Name");
    mongoDBCollectionPanel.add(mdbteamsCollectionNameTextField,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    mongoDBCollectionNameLable = new JLabel();
    mongoDBCollectionNameLable.setBackground(new Color(-2039585));
    Font mongoDBCollectionNameLableFont = this
        .$$$getFont$$$("TheSans", Font.BOLD, 18, mongoDBCollectionNameLable.getFont());
    if (mongoDBCollectionNameLableFont != null) {
      mongoDBCollectionNameLable.setFont(mongoDBCollectionNameLableFont);
    }
    mongoDBCollectionNameLable.setForeground(new Color(-4973171));
    mongoDBCollectionNameLable.setHorizontalAlignment(0);
    mongoDBCollectionNameLable.setHorizontalTextPosition(0);
    mongoDBCollectionNameLable.setText("MongoDB Collection Names");
    mongoDBCollectionPanel.add(mongoDBCollectionNameLable,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    mysqlTablePanel = new JPanel();
    mysqlTablePanel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
    mysqlTablePanel.setBackground(new Color(-2039585));
    databaseInputPanel.add(mysqlTablePanel,
        new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    mysqlTablePanel.setBorder(BorderFactory
        .createTitledBorder(BorderFactory.createLineBorder(new Color(-4973171)), null));
    sqlschoolsTableNameTextField = new JTextField();
    sqlschoolsTableNameTextField.setBackground(new Color(-2039585));
    Font sqlschoolsTableNameTextFieldFont = this
        .$$$getFont$$$("TheSans", -1, 14, sqlschoolsTableNameTextField.getFont());
    if (sqlschoolsTableNameTextFieldFont != null) {
      sqlschoolsTableNameTextField.setFont(sqlschoolsTableNameTextFieldFont);
    }
    sqlschoolsTableNameTextField.setForeground(new Color(-4973171));
    sqlschoolsTableNameTextField.setHorizontalAlignment(0);
    sqlschoolsTableNameTextField.setText("Schools Table Name");
    mysqlTablePanel.add(sqlschoolsTableNameTextField,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    sqlquestionsTableNameTextField = new JTextField();
    sqlquestionsTableNameTextField.setBackground(new Color(-2039585));
    Font sqlquestionsTableNameTextFieldFont = this
        .$$$getFont$$$("TheSans", -1, 14, sqlquestionsTableNameTextField.getFont());
    if (sqlquestionsTableNameTextFieldFont != null) {
      sqlquestionsTableNameTextField.setFont(sqlquestionsTableNameTextFieldFont);
    }
    sqlquestionsTableNameTextField.setForeground(new Color(-4973171));
    sqlquestionsTableNameTextField.setHorizontalAlignment(0);
    sqlquestionsTableNameTextField.setText("Questions Table Name");
    mysqlTablePanel.add(sqlquestionsTableNameTextField,
        new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    sqlteamsTableNameTextField = new JTextField();
    sqlteamsTableNameTextField.setBackground(new Color(-2039585));
    Font sqlteamsTableNameTextFieldFont = this
        .$$$getFont$$$("TheSans", -1, 14, sqlteamsTableNameTextField.getFont());
    if (sqlteamsTableNameTextFieldFont != null) {
      sqlteamsTableNameTextField.setFont(sqlteamsTableNameTextFieldFont);
    }
    sqlteamsTableNameTextField.setForeground(new Color(-4973171));
    sqlteamsTableNameTextField.setHorizontalAlignment(0);
    sqlteamsTableNameTextField.setText("Teams Table Name");
    mysqlTablePanel.add(sqlteamsTableNameTextField,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    mySqlTableLable = new JLabel();
    mySqlTableLable.setBackground(new Color(-2039585));
    Font mySqlTableLableFont = this
        .$$$getFont$$$("TheSans", Font.BOLD, 18, mySqlTableLable.getFont());
    if (mySqlTableLableFont != null) {
      mySqlTableLable.setFont(mySqlTableLableFont);
    }
    mySqlTableLable.setForeground(new Color(-4973171));
    mySqlTableLable.setHorizontalAlignment(0);
    mySqlTableLable.setHorizontalTextPosition(0);
    mySqlTableLable.setText("MySQL Table Names");
    mysqlTablePanel.add(mySqlTableLable,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    mySQLDatabasePanel = new JPanel();
    mySQLDatabasePanel.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
    mySQLDatabasePanel.setBackground(new Color(-2039585));
    databaseInputPanel.add(mySQLDatabasePanel,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    mySQLDatabasePanel.setBorder(BorderFactory
        .createTitledBorder(BorderFactory.createLineBorder(new Color(-4973171)), null));
    mySQLJDBCStringLabel = new JLabel();
    mySQLJDBCStringLabel.setBackground(new Color(-2039585));
    Font mySQLJDBCStringLabelFont = this
        .$$$getFont$$$("TheSans", Font.BOLD, 18, mySQLJDBCStringLabel.getFont());
    if (mySQLJDBCStringLabelFont != null) {
      mySQLJDBCStringLabel.setFont(mySQLJDBCStringLabelFont);
    }
    mySQLJDBCStringLabel.setForeground(new Color(-4973171));
    mySQLJDBCStringLabel.setHorizontalAlignment(0);
    mySQLJDBCStringLabel.setHorizontalTextPosition(0);
    mySQLJDBCStringLabel.setText("MySQL JDBC String");
    mySQLDatabasePanel.add(mySQLJDBCStringLabel,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    mySQLDatabaseNameLabel = new JLabel();
    mySQLDatabaseNameLabel.setBackground(new Color(-2039585));
    Font mySQLDatabaseNameLabelFont = this
        .$$$getFont$$$("TheSans", Font.BOLD, 18, mySQLDatabaseNameLabel.getFont());
    if (mySQLDatabaseNameLabelFont != null) {
      mySQLDatabaseNameLabel.setFont(mySQLDatabaseNameLabelFont);
    }
    mySQLDatabaseNameLabel.setForeground(new Color(-4973171));
    mySQLDatabaseNameLabel.setHorizontalAlignment(0);
    mySQLDatabaseNameLabel.setHorizontalTextPosition(0);
    mySQLDatabaseNameLabel.setText("MySQL Database Name");
    mySQLDatabasePanel.add(mySQLDatabaseNameLabel,
        new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    mySQLDatabaseNameTextField = new JTextField();
    mySQLDatabaseNameTextField.setBackground(new Color(-2039585));
    Font mySQLDatabaseNameTextFieldFont = this
        .$$$getFont$$$("TheSans", -1, 14, mySQLDatabaseNameTextField.getFont());
    if (mySQLDatabaseNameTextFieldFont != null) {
      mySQLDatabaseNameTextField.setFont(mySQLDatabaseNameTextFieldFont);
    }
    mySQLDatabaseNameTextField.setForeground(new Color(-4973171));
    mySQLDatabaseNameTextField.setHorizontalAlignment(0);
    mySQLDatabaseNameTextField.setText("MySQL Database Name");
    mySQLDatabasePanel.add(mySQLDatabaseNameTextField,
        new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST,
            GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    sqlconnectorStringTextField = new JTextArea();
    sqlconnectorStringTextField.setBackground(new Color(-4973171));
    Font sqlconnectorStringTextFieldFont = this
        .$$$getFont$$$("TheSans", -1, -1, sqlconnectorStringTextField.getFont());
    if (sqlconnectorStringTextFieldFont != null) {
      sqlconnectorStringTextField.setFont(sqlconnectorStringTextFieldFont);
    }
    sqlconnectorStringTextField.setForeground(new Color(-2039585));
    sqlconnectorStringTextField.setLineWrap(true);
    sqlconnectorStringTextField.setText("jdbc:mysql://<localhost:port>/<db_name>");
    sqlconnectorStringTextField.setWrapStyleWord(true);
    mySQLDatabasePanel.add(sqlconnectorStringTextField,
        new GridConstraints(1, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW,
            new Dimension(150, 150), new Dimension(150, 150), new Dimension(150, 200), 0, false));
    choiceDBPanel = new JPanel();
    choiceDBPanel.setLayout(new GridLayoutManager(1, 2, new Insets(8, 32, 8, 8), -1, -1));
    choiceDBPanel.setBackground(new Color(-2039585));
    databasePanel.add(choiceDBPanel,
        new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    choiceDBPanel.setBorder(BorderFactory
        .createTitledBorder(BorderFactory.createLineBorder(new Color(-4973171)), null));
    choiceOfDatabaseLabel = new JLabel();
    choiceOfDatabaseLabel.setBackground(new Color(-2039585));
    Font choiceOfDatabaseLabelFont = this
        .$$$getFont$$$("TheSans", Font.BOLD, 18, choiceOfDatabaseLabel.getFont());
    if (choiceOfDatabaseLabelFont != null) {
      choiceOfDatabaseLabel.setFont(choiceOfDatabaseLabelFont);
    }
    choiceOfDatabaseLabel.setForeground(new Color(-4973171));
    choiceOfDatabaseLabel.setHorizontalAlignment(0);
    choiceOfDatabaseLabel.setHorizontalTextPosition(0);
    choiceOfDatabaseLabel.setText("Choice of Database");
    choiceDBPanel.add(choiceOfDatabaseLabel,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    DBTypeComboBox = new JComboBox();
    DBTypeComboBox.setBackground(new Color(-2039585));
    Font DBTypeComboBoxFont = this.$$$getFont$$$("TheSans", -1, 14, DBTypeComboBox.getFont());
    if (DBTypeComboBoxFont != null) {
      DBTypeComboBox.setFont(DBTypeComboBoxFont);
    }
    DBTypeComboBox.setForeground(new Color(-4973171));
    final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
    defaultComboBoxModel1.addElement("MongoDB");
    defaultComboBoxModel1.addElement("MySQL");
    DBTypeComboBox.setModel(defaultComboBoxModel1);
    choiceDBPanel.add(DBTypeComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
    if (currentFont == null) {
      return null;
    }
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
    return new Font(resultName, style >= 0 ? style : currentFont.getStyle(),
        size >= 0 ? size : currentFont.getSize());
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return primaryPanel;
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
          GuiController.setConnection(mySqlConnection);
        } else {
          mongoDBConnection = new MongoDBConnection(
              new MongoDBUser(userName, password, mongoDatabaseName, schoolCollection,
                  teamCollection, questionCollection, mongoConnectorName));
          GuiController.setConnection(mongoDBConnection);
        }
      } catch (Exception e1) {
        e1.printStackTrace();
      }
      frame.dispose();
    }
  }
}
