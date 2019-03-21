package com.bth.analysis.Stats.helperobjects;

public class RoundOffStats {
  private final double mean;
  private final double median;
  private final double stddev;
  private final double variance;

  public RoundOffStats(double mean, double median, double stddev, double variance, double numOfDec) {
    this.mean = roundOff(mean, numOfDec);
    this.median = roundOff(median, numOfDec);
    this.stddev = roundOff(stddev, numOfDec);
    this.variance = roundOff(variance, numOfDec);
  }

  private double roundOff(double input, double numOfDec) {
    return Math.round(input * numOfDec) / numOfDec;
  }

  @Override
  public String toString() {
    return
        "Mean: [" + getMean() + "]\n" +
            "Median: [" + getMedian() + "]\n" +
            "Standard: [" + getStddev() + "]\n" +
            "Variance: [" + getVariance() + ']';
  }

  public double getMean() {
    return mean;
  }

  public double getMedian() {
    return median;
  }

  public double getStddev() {
    return stddev;
  }

  public double getVariance() {
    return variance;
  }
}
