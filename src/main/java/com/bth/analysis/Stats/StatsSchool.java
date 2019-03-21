package com.bth.analysis.Stats;

import java.util.Objects;

public class StatsSchool extends Stats {
  private final String school;
  private final double score;

  public StatsSchool(String school, double score, double mean, double median, double stddev, double variance) {
    super(stddev, mean, median, variance);
    this.score = score;
    this.school = school;
  }

  @Override
  public String toString() {
    return
        "TeamName: " + this.school + "\n" +
            "Score: " + this.score + "\n" +
            "All questions: \n" +
            "Mean: [" + getMean() + "]\n" +
            "Median: [" + getMedian() + "]\n" +
            "Standard: [" + getStddev() + "]\n" +
            "Variance: [" + getVariance() + ']';
  }

  @Override
  public boolean equals(final Object o) {
    if (this.getClass() != o.getClass()) return false;
    StatsSchool temp = (StatsSchool) o;
    return (school.equals(temp.getSchool()) && score == temp.getScore() &&
        getMean() == temp.getMean() &&
        getVariance() == temp.getVariance() &&
        getStddev() == temp.getStddev() &&
        getMedian() == temp.getMedian());
  }

  public String getSchool() {
    return school;
  }

  public double getScore() {
    return score;
  }

  public int hashCode() {
    return Objects.hash(school, score, getMean(), getMedian(), getStddev(), getVariance());
  }
}
