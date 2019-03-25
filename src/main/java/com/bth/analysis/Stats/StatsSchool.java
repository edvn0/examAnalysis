package com.bth.analysis.Stats;

import java.util.Objects;

public class StatsSchool {

  private final String school;
  private final Stats stats;

  public StatsSchool(String school, double score, double mean, double median, double stddev,
      double variance) {
    stats = new Stats(stddev, mean, median, variance, score);
    this.school = school;
  }

  @Override
  public String toString() {
    return
        "TeamName: " + this.school + "\n" +
            "Score: " + this.stats.getScore() + "\n" +
            "All questions: \n" +
            "Mean: [" + getMean() + "]\n" +
            "Median: [" + getMedian() + "]\n" +
            "Standard: [" + getStddev() + "]\n" +
            "Variance: [" + getVariance() + ']';
  }

  @Override
  public boolean equals(final Object o) {
    if (this.getClass() != o.getClass()) {
      return false;
    }
    StatsSchool temp = (StatsSchool) o;
    return (school.equals(temp.getSchool()) && this.stats.getScore() == temp.getScore() &&
        getMean() == temp.getMean() &&
        getVariance() == temp.getVariance() &&
        getStddev() == temp.getStddev() &&
        getMedian() == temp.getMedian());
  }

  public String getSchool() {
    return school;
  }

  public double getScore() {
    return this.stats.getScore();
  }

  public int hashCode() {
    return Objects
        .hash(school, this.stats.getScore(), getMean(), getMedian(), getStddev(), getVariance());
  }

  public double getVariance() {
    return stats.getVariance();
  }

  public double getStddev() {
    return stats.getStddev();
  }

  public double getMean() {
    return stats.getMean();
  }

  public double getMedian() {
    return stats.getMedian();
  }
}