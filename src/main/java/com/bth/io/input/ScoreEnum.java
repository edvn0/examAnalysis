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

public enum ScoreEnum {
  qOne(10, 1),
  qTwo(2, 2),
  qThree(4, 3),
  qFour(4, 4),
  qFive(10, 5),
  qSix(10, 6),
  qSeven(5, 7),
  qEight(5, 8),
  qNine(5, 9),
  qTen(5, 10),
  qEleven(5, 11),
  qTwelve(5, 12),
  qThirteen(5, 13),
  qFourteen(5, 14);

  public final int max;
  public final int index;

  ScoreEnum(int max, int index) {
    this.max = max;
    this.index = index;
  }
}