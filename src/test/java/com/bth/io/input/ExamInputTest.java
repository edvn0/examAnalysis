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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.bth.exams.ExamSchool;
import com.bth.gui.controller.loginusers.SQLLoginUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExamInputTest {

  private ExamInput input;

  @Before
  public void init() {
    input = new ExamInput(new SQLLoginUser("Edwin", "Edwin98".toCharArray(),
        "answers_from_exams", "jdbc:mysql://localhost:8889/"
        + "stats_exams?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC"));
    assertNotNull(input);
  }

  @After
  public void close() {
    input = null;
    assertNull(input);
  }

  @Test
  public void getMaxScore1() {
    int max = input.getMaxScore("q1");

    assertEquals("Max score for question 1 should be 10", 10, max);
  }

  @Test
  public void getMaxScore2() {
    int max = input.getMaxScore("q14");

    assertNotEquals("Should not equal the correct value of 5", 2, max);
  }

  @Test
  public void getSchoolIndex() {

  }

  @Test
  public void getAmountOfExamsFromSameSchool() {
    ExamSchool[] schools = input.getExamSchools();
    String[] strings = input.schools;

    ExamSchool[] use = {schools[1], schools[67]};
    // Handelshögskolan i Göteborg (index 2) och Luleå tekniska universitet (index 12)

    int[] amount = input.getAmountOfExamsFromSameSchool(schools, strings);

    assertEquals("Should return 6 exams for Handels i Göteborg", 6, amount[2]);
    assertEquals("Should return 6 exams for Luleå TH", 7, amount[12]);
  }
}