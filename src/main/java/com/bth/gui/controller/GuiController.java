package com.bth.gui.controller;

import com.bth.analysis.stats.StatsSchool;
import com.bth.analysis.stats.StatsTeam;
import com.bth.analysis.stats.helperobjects.RoundOffStatsQuestion;
import com.bth.gui.MainGUI.MainGui;
import com.bth.gui.csvchooser.CsvDirectoryChoice;
import com.bth.gui.dbinput.ExamsFromDatabaseInput;
import com.bth.gui.examdirectorygui.ChooseInputFileFrame;
import com.bth.gui.login.LoginDatabase;
import com.bth.io.input.ExamInput;
import com.bth.io.output.database.mongodb.mongodbconnector.ExamMongoDBObject;
import com.bth.io.output.database.mongodb.mongodbconnector.MongoDBConnection;
import com.bth.io.output.database.sql.sqlconnector.MySqlConnection;
import com.mongodb.BasicDBObject;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

public class GuiController {

  private static MongoDBConnection mongoDBConnection;
  private static MySqlConnection mySqlConnection;

  private CsvDirectoryChoice csvDirectoryChoice;
  private LoginDatabase database;
  private MainGui mainGui;
  private ChooseInputFileFrame chooseInputFileFrame;
  private ExamsFromDatabaseInput examsFromDatabaseInput;

  public GuiController() {
    csvDirectoryChoice = null;
    database = null;
    mainGui = null;
    chooseInputFileFrame = null;
  }

  public static void setConnection(MySqlConnection conn) {
    mySqlConnection = conn;
  }

  public static void setConnection(MongoDBConnection conn) {
    mongoDBConnection = conn;
  }

  public MongoDBConnection getMDBConnection() {
    return this.database.getMongoDBConnection();
  }

  public MySqlConnection getMySqlConnection() {
    return this.database.getMySqlConnection();
  }

  public void setEnabledForAll(ArrayList<JComponent> component, boolean value) {
    for (JComponent c : component) {
      c.setEnabled(value);
    }
  }

  public void append(LoginDatabase database) {
    this.database = database;
  }

  public void append(CsvDirectoryChoice csvDirectoryChoice) {
    this.csvDirectoryChoice = csvDirectoryChoice;
  }

  public void append(MainGui gui) {
    this.mainGui = gui;
  }

  public void append(ExamsFromDatabaseInput examsFromDatabaseInput) {
    this.examsFromDatabaseInput = examsFromDatabaseInput;
  }

  public void append(ChooseInputFileFrame chooseInputFileFrame) {
    this.chooseInputFileFrame = chooseInputFileFrame;
  }

  public LoginDatabase getLoginDatabase() {
    return database;
  }

  public CsvDirectoryChoice getCsvDirectoryChoice() {
    return csvDirectoryChoice;
  }

  public MainGui getMainGui() {
    return mainGui;
  }

  public void insertIntoMongoDatabase(List<StatsSchool> schoolList,
      List<StatsTeam> teamList,
      List<RoundOffStatsQuestion> questionList,
      String schoolColl, String teamColl, String questionColl) {
    boolean schoolListNull = schoolList == null;
    boolean teamListNull = teamList == null;
    boolean questionListNull = questionList == null;
    boolean questionsAreNotNull =
        schoolListNull && teamListNull && !questionListNull; // => we will insert questions
    boolean teamsAreNotNull =
        schoolListNull && questionListNull && !teamListNull; // => we will insert teams
    boolean schoolsAreNotNull =
        teamListNull && questionListNull && !schoolListNull; // => we will insert schools

    List<BasicDBObject> objects = new ArrayList<>();

    String use = null;

    if (schoolsAreNotNull) {
      for (StatsSchool school : schoolList) {
        objects.add(ExamMongoDBObject.toDBObject(school));
      }
      use = schoolColl;
    } else if (teamsAreNotNull) {
      for (StatsTeam team : teamList) {
        objects.add(ExamMongoDBObject.toDBObject(team));
      }
      use = teamColl;
    } else if (questionsAreNotNull) {
      for (RoundOffStatsQuestion question : questionList) {
        objects.add(ExamMongoDBObject.toDBObject(question));
      }
      use = questionColl;
    }
    mongoDBConnection.getMongoDatabase().getCollection(use, BasicDBObject.class)
        .insertMany(objects);
  }

  /***
   * <p>Inserts teams, schools or questions or all into both or one database.</p>
   * @param teamList List of teams
   * @param schoolList List of schools
   * @param rosqList List of questions
   * @param table table in SQL,
   * @throws SQLException if NoInputFound
   */
  @SuppressWarnings("all")
  public void insertIntoMySQLDatabase(List<StatsTeam> teamList,
      List<StatsSchool> schoolList,
      List<RoundOffStatsQuestion> rosqList, String table) throws SQLException {

    if (mySqlConnection == null && mongoDBConnection == null) {
      throw new NullPointerException(
          "MySQL Connection and MongoDB Connection were not online. Try again.");
    } else if (mySqlConnection != null && mongoDBConnection == null) {
      System.out.println("You will only input into your SQL database now.");
    } else if (mySqlConnection == null && mongoDBConnection != null) {
      System.out.println("You will only input into you Mongo database now.");
    } else if (mySqlConnection == null) {
      throw new NullPointerException("MySQL Connection was not online. Try again.");
    } else if (mongoDBConnection == null) {
      throw new NullPointerException("MongoDB Connection was not online. Try again.");
    }

    if (teamList == null && schoolList != null && rosqList == null) {
      for (StatsSchool school : schoolList) {
        String sql = "insert into "
            + mySqlConnection.getUser().getDatabaseName()
            + "."
            + table
            + " (name,score,mean,standarddev,variance,median) values (?,?,?,?,?,?)";

        PreparedStatement statement = mySqlConnection.getConnection().prepareStatement(sql);

        statement = setStatementWithPS(statement,
            school.getScore(),
            school.getMean(),
            school.getStddev(),
            school.getVariance(),
            school.getMedian(),
            null,
            school,
            null,
            0);

        statement.execute();
      }
    } else if (schoolList == null && teamList != null && rosqList == null) {

      for (StatsTeam team : teamList) {
        String sql = "insert into "
            + mySqlConnection.getUser().getDatabaseName()
            + "."
            + table
            + " (name,score,mean,standarddev,variance,median) values (?,?,?,?,?,?)";

        PreparedStatement statement = mySqlConnection.getConnection().prepareStatement(sql);

        statement = setStatementWithPS(statement,
            team.getScore(),
            team.getMean(),
            team.getStddev(),
            team.getVariance(),
            team.getMedian(),
            team,
            null,
            null,
            0);

        statement.execute();
      }
    } else if (schoolList == null && teamList == null && rosqList != null) {

      // Inserts the questions 1-14 into stats_exams.questions
      // if the questions already do not exist.
      boolean doesQuestionsExist = checkDatabaseForQuestions(rosqList);

      if (!doesQuestionsExist) {
        System.out.println("*-------------------------------------------------------*");
        System.out.println("Inserting questions into database, instead of updating..");
        System.out.println("*-------------------------------------------------------*");
        for (RoundOffStatsQuestion question : rosqList) {
          String q = Integer.toString(Integer.parseInt(question.getQuestion()) + 1);
          String questionString = "q".concat(q);

          // Parametrise an SQL query insertion for every RoSQ.
          String sql = "insert into "
              + mySqlConnection.getUser().getDatabaseName()
              + "."
              + table
              + " (question, date, mean, median, stddev, variance, max_score) "
              + "values (?,?,?,?,?,?,?)";

          int maxScore = ExamInput.getMaxScore(q);
          if (maxScore == -1) {
            throw new SQLException("Max Score was not found.");
          } else {
            PreparedStatement statement = mySqlConnection.getConnection().prepareStatement(sql);
            statement = setStatementWithPS(statement,
                0,
                question.getMean(),
                question.getStddev(),
                question.getVariance(),
                question.getMedian(),
                null,
                null,
                questionString,
                maxScore);

            statement.execute();
          }
        }
      } else {

        System.out.println("*-------------------------------------------------------*");
        System.out.println("Updating entries instead...");
        System.out.println("*-------------------------------------------------------*");

        for (RoundOffStatsQuestion question : rosqList) {
          String q = Integer.toString(Integer.parseInt(question.getQuestion()) + 1);
          String questionString = "q".concat(q);

          String sql = "UPDATE "
              + mySqlConnection.getUser().getDatabaseName()
              + "."
              + table
              + " SET mean = ?, stddev = ?, variance = ?, median = ? WHERE question = ?";

          PreparedStatement statement = mySqlConnection.getConnection().prepareStatement(sql);
          statement.setBigDecimal(1, new BigDecimal(question.getMean()));
          statement.setBigDecimal(2, new BigDecimal(question.getStddev()));
          statement.setBigDecimal(3, new BigDecimal(question.getVariance()));
          statement.setBigDecimal(4, new BigDecimal(question.getMedian()));
          statement.setString(5, questionString);

          statement.execute();
        }
      }
    } else {
      mySqlConnection.getConnection().close();
      System.err.println("Error! Input either schoolexams or teamexams.");
      System.exit(0);
    }
  }

  private PreparedStatement setStatementWithPS(PreparedStatement statement, double score,
      double mean, double stddev, double variance,
      double median, StatsTeam team, StatsSchool school,
      String question, int maxscore) throws SQLException {

    if (question != null && school == null && team == null) {

      BigDecimal bdMean = new BigDecimal(mean);
      BigDecimal bdMedian = new BigDecimal(median);
      BigDecimal bdStddev = new BigDecimal(stddev);
      BigDecimal bdVariance = new BigDecimal(variance);

      statement.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
      statement.setString(1, question);
      statement.setBigDecimal(3, bdMean);
      statement.setBigDecimal(4, bdMedian);
      statement.setBigDecimal(5, bdStddev);
      statement.setBigDecimal(6, bdVariance);
      statement.setInt(7, maxscore);

    } else if (team == null) {
      statement.setString(1, school.getSchool());
      writeStringToStatement(statement, score, mean, stddev, variance, median);

    } else if (school == null) {

      statement.setString(1, team.getTeam());
      writeStringToStatement(statement, score, mean, stddev, variance, median);

    }
    return statement;
  }

  /***
   *<p>Checks the database Connection if in the questions database,
   * there are already questions..</p>
   * @param rosqList list of the questions in the database
   * @return true if they exist. ("Not" what we want.)
   */
  @SuppressWarnings("all")
  private boolean checkDatabaseForQuestions(List<RoundOffStatsQuestion> rosqList) {
    try {
      ResultSet rs = mySqlConnection.getConnection()
          .prepareStatement("SELECT * FROM stats_exams.questions").executeQuery();

      int k = 0;
      while (rs.next()) {
        String question = rs.getString(2);
        double mean = Double.parseDouble(rs.getString(4));
        double median = Double.parseDouble(rs.getString(5));
        double stddev = Double.parseDouble(rs.getString(6));
        double variance = Double.parseDouble(rs.getString(7));
        double maxscore = Double.parseDouble(rs.getString(8));
        String q = Integer.toString(Integer.parseInt(rosqList.get(k++).getQuestion()) + 1);
        String questionString = "q".concat(q);
        if (!(question.equals(questionString) && mean >= 0 && median >= 0 && stddev >= 0
            && variance >= 0
            && maxscore >= 0)) {
          return true;
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getSQLState());
    }
    return false;
  }

  private void writeStringToStatement(PreparedStatement statement, double score,
      double mean, double stddev, double variance,
      double median) throws SQLException {
    statement.setString(2, "" + score);
    statement.setString(3, "" + mean);
    statement.setString(4, "" + stddev);
    statement.setString(5, "" + variance);
    statement.setString(6, "" + median);
  }

  public ChooseInputFileFrame getChooseInputFileFrame() {
    return this.chooseInputFileFrame;
  }

  public void setChooseInputFileFrame(ChooseInputFileFrame fileFrame) {
    this.chooseInputFileFrame = fileFrame;
  }

  public ExamsFromDatabaseInput getExamsFromDatabaseInput() {
    return this.examsFromDatabaseInput;
  }
}
