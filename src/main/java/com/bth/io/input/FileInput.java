/*
 * Copyright (c) 2018-2019 Edwin Carlsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.bth.io.input;

import com.bth.gui.controller.loginusers.SQLLoginUser;
import com.bth.io.output.database.sql.sqlconnector.MySqlConnection;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileInput {

  private Scanner scanner;
  private MySqlConnection connection;

  FileInput(String dir) throws FileNotFoundException {
    scanner = new Scanner(new File(dir));
  }

  public FileInput(SQLLoginUser user) {
    connection = new MySqlConnection(user);
  }

  /***
   * Uses the SQL Login-Connection system to dynamically move input
   * files from a file structure to a database structure instead.
   * You will here specify your input database.
   * @return ArrayList<String [ ]> with all rows in database.
   */
  public List<String[]> fileInputFromDatabase() {
    PreparedStatement statement;
    ResultSet set;
    List<String[]> strings = new ArrayList<>();
    try {
      statement = connection.getConnection()
          .prepareStatement("select * from stats_exams.answers_from_exams");
      set = statement.executeQuery();

      while (set.next()) {
        String[] temp = new String[20];
        for (int i = 0; i < temp.length; i++) {
          temp[i] = set.getString(i + 1);
        }
        strings.add(temp);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return strings;
  }

  List<String[]> fileInput() {
    List<String[]> values = new ArrayList<>();
    while (scanner.hasNext()) {
      values.add(scanner.nextLine().split(","));
    }
    return values;
  }

  /***
   * Returns the index of where the questions start or end.
   * @param startorend <code>true</code> if where they start in the array, <code>false</code> otherwise
   * @param data the input <code>List<String[]></code> from the input tsv file.
   * @return <code>int</code>
   */
  int getIndex(boolean startorend, List<String[]> data, String metaDataQ1, String metaDataQ14) {
    // Todo: identify correct rows.
    String question =
        startorend ? metaDataQ1.toLowerCase().trim() : metaDataQ14.toLowerCase().trim();

    String[] inData = data.get(0);
    int length = inData.length;

    for (int j = 0; j < length; j++) {
      String string = inData[j].trim().toLowerCase();
      if (string.equals(question)) {
        return startorend ? j : j + 1;
      }
    }
    return -1;
  }

  public ResultSet getResultSet() {
    try {
      return connection.getConnection()
          .prepareStatement("select * from stats_exams.answers_from_exams").executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public int getMetaDataQuestionIndex(ResultSet set, String q1, String q2, boolean start) {
    ResultSetMetaData metaData;
    try {
      metaData = set.getMetaData();
      for (int i = 1; i < metaData.getColumnCount(); i++) {

        String s = metaData.getColumnName(i).toLowerCase().trim();
        if (s.equals(q1) && start) {
          return i;
        } else if (s.equals(q2) && !start) {
          return i;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }
}
