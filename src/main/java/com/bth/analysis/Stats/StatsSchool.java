package com.bth.analysis.Stats;

import java.util.Objects;

public class StatsSchool extends Stats {

  private final String school;

  public StatsSchool(String school, double score, double mean, double median, double stddev,
      double variance) {
    super(stddev, mean, median, variance, score);
    this.school = school;
  }

  @Override
  public String toString() {
    return
        "TeamName: " + this.school + "\n" +
            "Score: " + super.getScore() + "\n" +
            "All questions: \n" +
            "Mean: [" + getMean() + "]\n" +
            "Median: [" + getMedian() + "]\n" +
            "Standard: [" + getStddev() + "]\n" +
            "Variance: [" + getVariance() + ']';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StatsSchool)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    StatsSchool school1 = (StatsSchool) o;
    return getSchool().equals(school1.getSchool());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getSchool());
  }

  public String getSchool() {
    return school;
  }
}