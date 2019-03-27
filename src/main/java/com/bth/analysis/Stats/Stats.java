package com.bth.analysis.Stats;

import java.util.Objects;

public class Stats {

  private double stddev, mean, median, variance, score;

  public Stats(double stddev, double mean, double median, double variance, double score) {
    this.stddev = stddev;
    this.mean = mean;
    this.median = median;
    this.variance = variance;
    this.score = score;
  }

  public Stats(double stddev, double mean, double median, double variance) {
    this.stddev = stddev;
    this.mean = mean;
    this.median = median;
    this.variance = variance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Stats)) {
      return false;
    }
    Stats stats = (Stats) o;
    return Double.compare(stats.getStddev(), getStddev()) == 0 &&
        Double.compare(stats.getMean(), getMean()) == 0 &&
        Double.compare(stats.getMedian(), getMedian()) == 0 &&
        Double.compare(stats.getVariance(), getVariance()) == 0 &&
        Double.compare(stats.getScore(), getScore()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getStddev(), getMean(), getMedian(), getVariance(), getScore());
  }

  public double getVariance() {
    return variance;
  }

  public double getStddev() {
    return stddev;
  }

  public double getMean() {
    return mean;
  }

  public double getMedian() {
    return median;
  }

  public double getScore() {
    return score;
  }
}
