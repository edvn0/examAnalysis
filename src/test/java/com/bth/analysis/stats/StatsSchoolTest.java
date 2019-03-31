/*
 * Copyright (c) 2018-2019 Edwin Carlsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.bth.analysis.stats;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class StatsSchoolTest {

  @Test
  public void equals1() {
    StatsSchool school = new StatsSchool("BTH", 40, 4.3, 3, 0.612, 1.22);
    StatsSchool school1 = new StatsSchool("BTH", 40, 4.3, 3, 0.612, 1.22);
    assertEquals(school, school1);
  }

  @Test
  public void notEquals() {
    StatsSchool school = new StatsSchool("BTH", 40, 4.3, 3, 0.612, 1.22);
    StatsSchool school1 = new StatsSchool("BTH", 40.1, 4.3, 3, 0.612, 1.22);
    assertNotEquals(school, school1);
  }
}