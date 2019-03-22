package com.bth.analysis.Stats;

import java.util.Objects;

public class StatsTeam extends Stats {

  private final String team;
  private final double score;

  public StatsTeam(String team, double score, double mean, double median, double stddev,
      double variance) {
    super(stddev, mean, median, variance);
    this.team = team;
    this.score = score;
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
            "Score: " + this.score + "\n" +
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
    return (team.equals(temp.getTeam()) && score == temp.getScore() &&
        getMean() == temp.getMean() &&
        getVariance() == temp.getVariance() &&
        getStddev() == temp.getStddev() &&
        getMedian() == temp.getMedian());
  }

  public String getTeam() {
    return team;
  }

  public double getScore() {
    return score;
  }

  @Override
  public int hashCode() {
    return Objects.hash(team, score, getMean(), getMedian(), getStddev(), getVariance());
  }


}
