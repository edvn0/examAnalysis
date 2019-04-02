package com.bth.analysis.stats;

import java.util.Objects;

/***
 * StatsSchool represents a school with the associated Stats.
 */
public class StatsSchool extends Stats {

  private final String school;

  public StatsSchool(final String school,
      final double score,
      final double mean,
      final double median,
      final double stddev,
      final double variance) {
    super(stddev, mean, median, variance, score);
    this.school = school;
  }

  @Override
  public String toString() {
    return
        "School Name: " + this.school + "\n"
            + "Score: " + super.getScore() + "\n"
            + "All questions: \n"
            + "Mean: [" + getMean() + "]\n"
            + "Median: [" + getMedian() + "]\n"
            + "Standard: [" + getStddev() + "]\n"
            + "Variance: [" + getVariance() + ']';
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
