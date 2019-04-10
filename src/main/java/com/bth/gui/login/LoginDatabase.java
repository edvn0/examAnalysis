package com.bth.gui.login;

import com.bth.gui.controller.GuiController;
import com.bth.gui.controller.loginusers.DatabaseLoginUser;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class LoginDatabase extends JPanel {

  private final ThreadLocal<PropertiesReader> reader = new ThreadLocal<>();
  private JFrame frame;
  private DatabaseLoginUser user;
  private MongoDBConnection mongoDBConnection;
  private MySqlConnection mySqlConnection;

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  // Generated using JFormDesigner Evaluation license - Edwin Carlsson
  public JPanel exitConfirmPanel;

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
  public JButton exitButton;
  public JButton confirmButton;
  public JPanel databasePanel;
  public JTextField UserName;
  public JPasswordField Password;
  public JPanel databaseInputPanel;
  public JPanel mongoDBNamePanel;
  public JTextField mongoDatabaseNameTextField;
  public JLabel mongoDbDatabaseNameLable;
  public JTextArea mongoDbConnectStringTextField;
  public JLabel mongoDbDatabaseNameLabel;
  public JPanel mongoDBCollectionPanel;
  public JTextField mdbschoolCollectionNameTextField;
  public JTextField mdbquestionsNameTextField;
  public JTextField mdbteamsCollectionNameTextField;
  public JLabel mongoDBCollectionNameLable;
  public JPanel mySQLDatabasePanel;
  public JLabel mySQLJDBCStringLabel;
  public JLabel mySQLDatabaseNameLabel;
  public JTextField mySQLDatabaseNameTextField;
  public JTextArea sqlconnectorStringTextField;
  public JPanel mysqlTablePanel;
  public JTextField sqlschoolsTableNameTextField;
  public JTextField sqlquestionsTableNameTextField;
  public JTextField sqlteamsTableNameTextField;
  public JLabel mySqlTableLable;
  public JPanel choiceDBPanel;
  public JLabel choiceOfDatabaseLabel;
  public JComboBox<String> DBTypeComboBox;
  public LoginDatabase() {
    initComponents();
    frame = new JFrame("Login to Database");
    frame.setContentPane(this);
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

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - Edwin Carlsson
    exitConfirmPanel = new JPanel();
    exitButton = new JButton();
    confirmButton = new JButton();
    databasePanel = new JPanel();
    UserName = new JTextField();
    Password = new JPasswordField();
    databaseInputPanel = new JPanel();
    mongoDBNamePanel = new JPanel();
    mongoDatabaseNameTextField = new JTextField();
    mongoDbDatabaseNameLable = new JLabel();
    mongoDbConnectStringTextField = new JTextArea();
    mongoDbDatabaseNameLabel = new JLabel();
    mongoDBCollectionPanel = new JPanel();
    mdbschoolCollectionNameTextField = new JTextField();
    mdbquestionsNameTextField = new JTextField();
    mdbteamsCollectionNameTextField = new JTextField();
    mongoDBCollectionNameLable = new JLabel();
    mySQLDatabasePanel = new JPanel();
    mySQLJDBCStringLabel = new JLabel();
    mySQLDatabaseNameLabel = new JLabel();
    mySQLDatabaseNameTextField = new JTextField();
    sqlconnectorStringTextField = new JTextArea();
    mysqlTablePanel = new JPanel();
    sqlschoolsTableNameTextField = new JTextField();
    sqlquestionsTableNameTextField = new JTextField();
    sqlteamsTableNameTextField = new JTextField();
    mySqlTableLable = new JLabel();
    choiceDBPanel = new JPanel();
    choiceOfDatabaseLabel = new JLabel();
    DBTypeComboBox = new JComboBox<>();

    //======== this ========
    setBackground(new Color(224, 224, 223));
    setForeground(new Color(224, 224, 223));
    setMaximumSize(new Dimension(1040, 550));
    setMinimumSize(new Dimension(1040, 550));
    setOpaque(false);
    setPreferredSize(new Dimension(1040, 550));

    // JFormDesigner evaluation mark
    setBorder(new javax.swing.border.CompoundBorder(
      new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
        "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
        javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
        java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

    setLayout(new GridLayoutManager(2, 1, new Insets(8, 8, 8, 8), -1, -1));

    //======== exitConfirmPanel ========
    {
      exitConfirmPanel.setBackground(new Color(224, 224, 223));
      exitConfirmPanel.setOpaque(true);
      exitConfirmPanel.setBorder(new TitledBorder(new LineBorder(new Color(180, 29, 141)), ""));
      exitConfirmPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));

      //---- exitButton ----
      exitButton.setBackground(new Color(224, 224, 223));
      exitButton.setFont(new Font("TheSans", exitButton.getFont().getStyle(), 14));
      exitButton.setForeground(new Color(180, 29, 141));
      exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
      exitButton.setOpaque(true);
      exitButton.setText("Exit");
      exitConfirmPanel.add(exitButton, new GridConstraints(0, 0, 1, 1,
        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
        null, null, null));

      //---- confirmButton ----
      confirmButton.setBackground(new Color(224, 224, 223));
      confirmButton.setFont(new Font("TheSans", confirmButton.getFont().getStyle(), 14));
      confirmButton.setForeground(new Color(180, 29, 141));
      confirmButton.setHorizontalTextPosition(SwingConstants.CENTER);
      confirmButton.setOpaque(true);
      confirmButton.setText("Confirm");
      exitConfirmPanel.add(confirmButton, new GridConstraints(0, 1, 1, 1,
        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
        null, null, null));
    }
    add(exitConfirmPanel, new GridConstraints(1, 0, 1, 1,
      GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
      GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
      GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
      null, null, null));

    //======== databasePanel ========
    {
      databasePanel.setBackground(new Color(224, 224, 223));
      databasePanel.setBorder(new TitledBorder(new LineBorder(new Color(180, 29, 141)), ""));
      databasePanel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));

      //---- UserName ----
      UserName.setBackground(new Color(224, 224, 223));
      UserName.setFont(new Font("TheSans", UserName.getFont().getStyle(), 14));
      UserName.setForeground(new Color(180, 29, 141));
      UserName.setHorizontalAlignment(SwingConstants.CENTER);
      UserName.setText("Username for DB");
      databasePanel.add(UserName, new GridConstraints(2, 0, 1, 2,
        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED,
        null, null, null));

      //---- Password ----
      Password.setBackground(new Color(224, 224, 223));
      Password.setFont(new Font("TheSans", Password.getFont().getStyle(), 14));
      Password.setForeground(new Color(180, 29, 141));
      Password.setHorizontalAlignment(SwingConstants.CENTER);
      Password.setText("Password");
      databasePanel.add(Password, new GridConstraints(3, 0, 1, 2,
        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED,
        null, null, null));

      //======== databaseInputPanel ========
      {
        databaseInputPanel.setBackground(new Color(224, 224, 223));
        databaseInputPanel.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));

        //======== mongoDBNamePanel ========
        {
          mongoDBNamePanel.setBackground(new Color(224, 224, 223));
          mongoDBNamePanel.setBorder(new TitledBorder(new LineBorder(new Color(180, 29, 141)), ""));
          mongoDBNamePanel.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));

          //---- mongoDatabaseNameTextField ----
          mongoDatabaseNameTextField.setBackground(new Color(224, 224, 223));
          mongoDatabaseNameTextField.setFont(new Font("TheSans", mongoDatabaseNameTextField.getFont().getStyle(), 14));
          mongoDatabaseNameTextField.setForeground(new Color(180, 29, 141));
          mongoDatabaseNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
          mongoDatabaseNameTextField.setInheritsPopupMenu(false);
          mongoDatabaseNameTextField.setText("Mongo Database Name");
          mongoDBNamePanel.add(mongoDatabaseNameTextField, new GridConstraints(5, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

          //---- mongoDbDatabaseNameLable ----
          mongoDbDatabaseNameLable.setBackground(new Color(224, 224, 223));
          mongoDbDatabaseNameLable.setFont(new Font("TheSans", Font.BOLD, 18));
          mongoDbDatabaseNameLable.setForeground(new Color(180, 29, 141));
          mongoDbDatabaseNameLable.setHorizontalAlignment(SwingConstants.CENTER);
          mongoDbDatabaseNameLable.setHorizontalTextPosition(SwingConstants.CENTER);
          mongoDbDatabaseNameLable.setText("MongoDB Connect String");
          mongoDBNamePanel.add(mongoDbDatabaseNameLable, new GridConstraints(0, 0, 1, 1,
            GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

          //---- mongoDbConnectStringTextField ----
          mongoDbConnectStringTextField.setBackground(new Color(180, 29, 141));
          mongoDbConnectStringTextField.setFont(new Font("TheSans", mongoDbConnectStringTextField.getFont().getStyle(), mongoDbConnectStringTextField.getFont().getSize()));
          mongoDbConnectStringTextField.setForeground(new Color(224, 224, 223));
          mongoDbConnectStringTextField.setLineWrap(true);
          mongoDbConnectStringTextField.setText("mongodb+srv://<user>:<password>@<cluster>.mongodb.net/test?retryWrites=true");
          mongoDbConnectStringTextField.setWrapStyleWord(true);
          mongoDBNamePanel.add(mongoDbConnectStringTextField, new GridConstraints(1, 0, 3, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
            new Dimension(150, 150), new Dimension(150, 150), new Dimension(150, 200)));

          //---- mongoDbDatabaseNameLabel ----
          mongoDbDatabaseNameLabel.setBackground(new Color(224, 224, 223));
          mongoDbDatabaseNameLabel.setFont(new Font("TheSans", Font.BOLD, 18));
          mongoDbDatabaseNameLabel.setForeground(new Color(180, 29, 141));
          mongoDbDatabaseNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
          mongoDbDatabaseNameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
          mongoDbDatabaseNameLabel.setText("MongoDB Database Name");
          mongoDBNamePanel.add(mongoDbDatabaseNameLabel, new GridConstraints(4, 0, 1, 1,
            GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));
        }
        databaseInputPanel.add(mongoDBNamePanel, new GridConstraints(0, 1, 1, 1,
          GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          null, null, null));

        //======== mongoDBCollectionPanel ========
        {
          mongoDBCollectionPanel.setBackground(new Color(224, 224, 223));
          mongoDBCollectionPanel.setBorder(new TitledBorder(new LineBorder(new Color(180, 29, 141)), ""));
          mongoDBCollectionPanel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));

          //---- mdbschoolCollectionNameTextField ----
          mdbschoolCollectionNameTextField.setDisabledTextColor(new Color(65, 33, 52));
          mdbschoolCollectionNameTextField.setFont(new Font("TheSans", mdbschoolCollectionNameTextField.getFont().getStyle(), 14));
          mdbschoolCollectionNameTextField.setForeground(new Color(180, 29, 141));
          mdbschoolCollectionNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
          mdbschoolCollectionNameTextField.setText("Schools Collection Name");
          mongoDBCollectionPanel.add(mdbschoolCollectionNameTextField, new GridConstraints(1, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

          //---- mdbquestionsNameTextField ----
          mdbquestionsNameTextField.setBackground(new Color(224, 224, 223));
          mdbquestionsNameTextField.setFont(new Font("TheSans", mdbquestionsNameTextField.getFont().getStyle(), 14));
          mdbquestionsNameTextField.setForeground(new Color(180, 29, 141));
          mdbquestionsNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
          mdbquestionsNameTextField.setText("Questions Collection Name");
          mongoDBCollectionPanel.add(mdbquestionsNameTextField, new GridConstraints(3, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

          //---- mdbteamsCollectionNameTextField ----
          mdbteamsCollectionNameTextField.setBackground(new Color(224, 224, 223));
          mdbteamsCollectionNameTextField.setFont(new Font("TheSans", mdbteamsCollectionNameTextField.getFont().getStyle(), 14));
          mdbteamsCollectionNameTextField.setForeground(new Color(180, 29, 141));
          mdbteamsCollectionNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
          mdbteamsCollectionNameTextField.setText("Teams Collection Name");
          mongoDBCollectionPanel.add(mdbteamsCollectionNameTextField, new GridConstraints(2, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

          //---- mongoDBCollectionNameLable ----
          mongoDBCollectionNameLable.setBackground(new Color(224, 224, 223));
          mongoDBCollectionNameLable.setFont(new Font("TheSans", Font.BOLD, 18));
          mongoDBCollectionNameLable.setForeground(new Color(180, 29, 141));
          mongoDBCollectionNameLable.setHorizontalAlignment(SwingConstants.CENTER);
          mongoDBCollectionNameLable.setHorizontalTextPosition(SwingConstants.CENTER);
          mongoDBCollectionNameLable.setText("MongoDB Collection Names");
          mongoDBCollectionPanel.add(mongoDBCollectionNameLable, new GridConstraints(0, 0, 1, 1,
            GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));
        }
        databaseInputPanel.add(mongoDBCollectionPanel, new GridConstraints(0, 3, 1, 1,
          GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          null, null, null));

        //======== mySQLDatabasePanel ========
        {
          mySQLDatabasePanel.setBackground(new Color(224, 224, 223));
          mySQLDatabasePanel.setBorder(new TitledBorder(new LineBorder(new Color(180, 29, 141)), ""));
          mySQLDatabasePanel.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));

          //---- mySQLJDBCStringLabel ----
          mySQLJDBCStringLabel.setBackground(new Color(224, 224, 223));
          mySQLJDBCStringLabel.setFont(new Font("TheSans", Font.BOLD, 18));
          mySQLJDBCStringLabel.setForeground(new Color(180, 29, 141));
          mySQLJDBCStringLabel.setHorizontalAlignment(SwingConstants.CENTER);
          mySQLJDBCStringLabel.setHorizontalTextPosition(SwingConstants.CENTER);
          mySQLJDBCStringLabel.setText("MySQL JDBC String");
          mySQLDatabasePanel.add(mySQLJDBCStringLabel, new GridConstraints(0, 0, 1, 1,
            GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

          //---- mySQLDatabaseNameLabel ----
          mySQLDatabaseNameLabel.setBackground(new Color(224, 224, 223));
          mySQLDatabaseNameLabel.setFont(new Font("TheSans", Font.BOLD, 18));
          mySQLDatabaseNameLabel.setForeground(new Color(180, 29, 141));
          mySQLDatabaseNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
          mySQLDatabaseNameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
          mySQLDatabaseNameLabel.setText("MySQL Database Name");
          mySQLDatabasePanel.add(mySQLDatabaseNameLabel, new GridConstraints(4, 0, 1, 1,
            GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

          //---- mySQLDatabaseNameTextField ----
          mySQLDatabaseNameTextField.setBackground(new Color(224, 224, 223));
          mySQLDatabaseNameTextField.setFont(new Font("TheSans", mySQLDatabaseNameTextField.getFont().getStyle(), 14));
          mySQLDatabaseNameTextField.setForeground(new Color(180, 29, 141));
          mySQLDatabaseNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
          mySQLDatabaseNameTextField.setText("MySQL Database Name");
          mySQLDatabasePanel.add(mySQLDatabaseNameTextField, new GridConstraints(5, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

          //---- sqlconnectorStringTextField ----
          sqlconnectorStringTextField.setBackground(new Color(180, 29, 141));
          sqlconnectorStringTextField.setFont(new Font("TheSans", sqlconnectorStringTextField.getFont().getStyle(), sqlconnectorStringTextField.getFont().getSize()));
          sqlconnectorStringTextField.setForeground(new Color(224, 224, 223));
          sqlconnectorStringTextField.setLineWrap(true);
          sqlconnectorStringTextField.setText("jdbc:mysql://<localhost:port>/<db_name>");
          sqlconnectorStringTextField.setWrapStyleWord(true);
          mySQLDatabasePanel.add(sqlconnectorStringTextField, new GridConstraints(1, 0, 3, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
            new Dimension(150, 150), new Dimension(150, 150), new Dimension(150, 200)));
        }
        databaseInputPanel.add(mySQLDatabasePanel, new GridConstraints(0, 0, 1, 1,
          GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          null, null, null));

        //======== mysqlTablePanel ========
        {
          mysqlTablePanel.setBackground(new Color(224, 224, 223));
          mysqlTablePanel.setBorder(new TitledBorder(new LineBorder(new Color(180, 29, 141)), ""));
          mysqlTablePanel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));

          //---- sqlschoolsTableNameTextField ----
          sqlschoolsTableNameTextField.setBackground(new Color(224, 224, 223));
          sqlschoolsTableNameTextField.setFont(new Font("TheSans", sqlschoolsTableNameTextField.getFont().getStyle(), 14));
          sqlschoolsTableNameTextField.setForeground(new Color(180, 29, 141));
          sqlschoolsTableNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
          sqlschoolsTableNameTextField.setText("Schools Table Name");
          mysqlTablePanel.add(sqlschoolsTableNameTextField, new GridConstraints(1, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

          //---- sqlquestionsTableNameTextField ----
          sqlquestionsTableNameTextField.setBackground(new Color(224, 224, 223));
          sqlquestionsTableNameTextField.setFont(new Font("TheSans", sqlquestionsTableNameTextField.getFont().getStyle(), 14));
          sqlquestionsTableNameTextField.setForeground(new Color(180, 29, 141));
          sqlquestionsTableNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
          sqlquestionsTableNameTextField.setText("Questions Table Name");
          mysqlTablePanel.add(sqlquestionsTableNameTextField, new GridConstraints(3, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

          //---- sqlteamsTableNameTextField ----
          sqlteamsTableNameTextField.setBackground(new Color(224, 224, 223));
          sqlteamsTableNameTextField.setFont(new Font("TheSans", sqlteamsTableNameTextField.getFont().getStyle(), 14));
          sqlteamsTableNameTextField.setForeground(new Color(180, 29, 141));
          sqlteamsTableNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
          sqlteamsTableNameTextField.setText("Teams Table Name");
          mysqlTablePanel.add(sqlteamsTableNameTextField, new GridConstraints(2, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

          //---- mySqlTableLable ----
          mySqlTableLable.setBackground(new Color(224, 224, 223));
          mySqlTableLable.setFont(new Font("TheSans", Font.BOLD, 18));
          mySqlTableLable.setForeground(new Color(180, 29, 141));
          mySqlTableLable.setHorizontalAlignment(SwingConstants.CENTER);
          mySqlTableLable.setHorizontalTextPosition(SwingConstants.CENTER);
          mySqlTableLable.setText("MySQL Table Names");
          mysqlTablePanel.add(mySqlTableLable, new GridConstraints(0, 0, 1, 1,
            GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));
        }
        databaseInputPanel.add(mysqlTablePanel, new GridConstraints(0, 2, 1, 1,
          GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          null, null, null));
      }
      databasePanel.add(databaseInputPanel, new GridConstraints(1, 0, 1, 2,
        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        null, null, null));

      //======== choiceDBPanel ========
      {
        choiceDBPanel.setBackground(new Color(224, 224, 223));
        choiceDBPanel.setBorder(new TitledBorder(new LineBorder(new Color(180, 29, 141)), ""));
        choiceDBPanel.setLayout(new GridLayoutManager(1, 2, new Insets(8, 32, 8, 8), -1, -1));

        //---- choiceOfDatabaseLabel ----
        choiceOfDatabaseLabel.setBackground(new Color(224, 224, 223));
        choiceOfDatabaseLabel.setFont(new Font("TheSans", Font.BOLD, 18));
        choiceOfDatabaseLabel.setForeground(new Color(180, 29, 141));
        choiceOfDatabaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        choiceOfDatabaseLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        choiceOfDatabaseLabel.setText("Choice of Database");
        choiceDBPanel.add(choiceOfDatabaseLabel, new GridConstraints(0, 0, 1, 1,
          GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
          GridConstraints.SIZEPOLICY_FIXED,
          GridConstraints.SIZEPOLICY_FIXED,
          null, null, null));

        //---- DBTypeComboBox ----
        DBTypeComboBox.setBackground(new Color(224, 224, 223));
        DBTypeComboBox.setFont(new Font("TheSans", DBTypeComboBox.getFont().getStyle(), 14));
        DBTypeComboBox.setForeground(new Color(180, 29, 141));
        DBTypeComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
          "MongoDB",
          "MySQL"
        }));
        choiceDBPanel.add(DBTypeComboBox, new GridConstraints(0, 1, 1, 1,
          GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
          GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_FIXED,
          null, null, null));
      }
      databasePanel.add(choiceDBPanel, new GridConstraints(0, 0, 1, 2,
        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        null, null, null));
    }
    add(databasePanel, new GridConstraints(0, 0, 1, 1,
      GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
      GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
      GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
      null, null, null));
    // JFormDesigner - End of component initialization  //GEN-END:initComponents
  }
  // JFormDesigner - End of variables declaration  //GEN-END:variables
}
