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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.bth.gui.controller.loginusers.SQLLoginUser;
import java.sql.ResultSet;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileInputTest {

  private FileInput fileInput;

  @Before
  public void init() {
    fileInput = new FileInput(new SQLLoginUser("Edwin", "Edwin98".toCharArray(),
        "answers_from_exams", "jdbc:mysql://localhost:8889/"
        + "stats_exams?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC"));
    assertNotNull(fileInput);
  }

  @After
  public void close() {
    fileInput = null;
    assertNull(fileInput);
  }

  @Test
  public void fileInputFromDatabaseTest() {

    List<String[]> strings = fileInput.fileInputFromDatabase();
    boolean willPass = true;

    for (String[] arr : strings) {
      for (String s : arr) {
        if (s == null || s.equals("") || s.equals(" ")) {
          willPass = false;
          break;
        }
      }
    }
    assertTrue("Should be true", willPass);
  }

  @Test
  public void getIndexStartTest() {
    ResultSet set = fileInput.getResultSet();

    int index = fileInput.getMetaDataQuestionIndex("q1", "q14", true);

    assertEquals("Index should be 5, where the questions start", 5, index);
  }

  @Test
  public void getIndexEndTest() {
    ResultSet set = fileInput.getResultSet();

    int index = fileInput.getMetaDataQuestionIndex("q1", "q14", false);

    assertEquals("Index should be 18, where the questions end", 18, index);
  }

  @Test
  public void validateConnectorString_1() {
    String connector = "jdbc:mysql://localhost:8889/stats_exams?"
        + "zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
    assertTrue(FileInput.validateConnectorString(connector));
  }

  @Test
  public void validateConnectorString_2() {
    String connector = "jdbc:notSQL://localhost:8889/stats_exams?"
        + "zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
    assertFalse(FileInput.validateConnectorString(connector));
  }

  @Test
  public void validateConnectorString_3() {
    String connector = "mongodb+srv://edwin-carlsson:Edwin98@"
        + "examanalysiscluster-hsaye.mongodb.net/test?retryWrites=true";
    assertTrue(FileInput.validateConnectorString(connector));
  }

  @Test
  public void validateConnectorString_4() {
    String connector = "notMongoDB+srv://edwin-carlsson:Edwin98@"
        + "examanalysiscluster-hsaye.mongodb.net/test?retryWrites=true";
    assertFalse(FileInput.validateConnectorString(connector));
  }

  @Test
  public void splitConnectorString_1() {
    String connector = "mongodb+srv://edwin-carlsson:Edwin98@"
        + "examanalysiscluster-hsaye.mongodb.net/test?retryWrites=true";
    String[] strings = FileInput.splitConnectorString(connector);

    assertEquals(
        "MongoDB string should split into 'mongodb+srv' and '<username>:<password>:cluster...'",
        "mongodb+srv:", strings[0]);

  }

  @Test
  public void splitConnectorString_2() {
    String connector = "jdbc:mysql://localhost:8889/stats_exams?"
        + "zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
    String[] strings = FileInput.splitConnectorString(connector);

    assertEquals("JDBC string should be split into 'jdbc:mysql://' and '<host>/...'",
        "jdbc:mysql:", strings[0]);
    assertEquals("JDBC string should be split into 'jdbc:mysql://' and '<host>/...'",
        "localhost:8889/stats_exams?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC",
        strings[1]);

  }

  @Test
  public void splitConnectorString_3() {
    String connector = "jdbc:mysql:localhost:8889stats_exams?"
        + "zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
    String[] strings = FileInput.splitConnectorString(connector);

    String[] strings2 = new String[connector.length()];

    assertArrayEquals("Should contain the same elements (null) since no there is no regex matching",
        strings, strings2);
  }
}