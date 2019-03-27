package com.bth.analysis.Stats;

import java.util.Objects;

public class StatsTeam extends Stats {

  private final String team;

  public StatsTeam(String team, double score, double mean, double median, double stddev,
      double variance) {
    super(stddev, mean, median, variance, score);
    this.team = team;
  }

  /*
   * TeamName: Name(String)
   * Poäng: Score(Int)
   * Stats: [Stddev, Mean, Median, Variance]
   */
  @Override
  public String toString() {
    return
        "TeamName: " + this.team + "\n" +
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
    if (!(o instanceof StatsTeam)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    StatsTeam team1 = (StatsTeam) o;
    return getTeam().equals(team1.getTeam());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getTeam());
  }

  public String getTeam() {
    return team;
  }
}

