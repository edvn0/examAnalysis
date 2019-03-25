package com.bth.analysis.Stats;

import java.util.Objects;

public class StatsTeam {

  private final String team;
  private final Stats stats;

  public StatsTeam(String team, double score, double mean, double median, double stddev,
      double variance) {
    stats = new Stats(stddev, mean, median, variance, score);
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
    StatsTeam temp = (StatsTeam) o;
    return (team.equals(temp.getTeam()) && this.stats.getScore() == temp.getScore() &&
        getMean() == temp.getMean() &&
        getVariance() == temp.getVariance() &&
        getStddev() == temp.getStddev() &&
        getMedian() == temp.getMedian());
  }

  public String getTeam() {
    return team;
  }

  public double getScore() {
    return this.stats.getScore();
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(team, this.stats.getScore(), getMean(), getMedian(), getStddev(), getVariance());
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
