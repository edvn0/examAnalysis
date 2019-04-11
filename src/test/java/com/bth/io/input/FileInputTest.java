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

import static org.junit.Assert.assertEquals;
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

    int index = fileInput.getMetaDataQuestionIndex(set, "q1", "q14", true);

    assertEquals("Index should be 5, where the questions start", 5, index);
  }

  @Test
  public void getIndexEndTest() {
    ResultSet set = fileInput.getResultSet();

    int index = fileInput.getMetaDataQuestionIndex(set, "q1", "q14", false);

    assertEquals("Index should be 18, where the questions end", 18, index);
  }
}