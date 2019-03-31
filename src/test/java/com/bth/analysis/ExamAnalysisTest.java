/*
 * Copyright (c) 2018-2019 Edwin Carlsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.bth.analysis;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

public class ExamAnalysisTest {

  private final double[] VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
  private final ExamAnalysis analysis = new ExamAnalysis();

  @Test
  public void generateMean() {
    double mean = analysis.generateMean(VALUES, VALUES.length);
    assertEquals(mean, 5.5, Math.pow(10, -5));
  }

  @Test
  public void generateOddMedian() {
    double[] oddValues = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    double median = analysis.generateMedian(oddValues);
    Assert.assertEquals(median, 5d, Math.pow(10, -5));
  }

  @Test
  public void generateEvenMedian() {
    double median = analysis.generateMedian(VALUES);
    Assert.assertEquals(median, (5d + 6d) / 2, Math.pow(10, -3));
  }


  @Test
  public void generateVariance() {
    double variance = analysis
        .generateVariance(VALUES, analysis.generateMean(VALUES, VALUES.length), VALUES.length);

    Assert.assertEquals(variance, 8.250000d, Math.pow(10, -5));
  }

  @Test
  public void generateStandardDeviation() {
    double stddev = analysis
        .generateStandardDeviation(analysis.generateMean(VALUES, VALUES.length), VALUES.length,
            VALUES);
    Assert.assertEquals(stddev, 2.8722813d, Math.pow(10, -5));
  }
}