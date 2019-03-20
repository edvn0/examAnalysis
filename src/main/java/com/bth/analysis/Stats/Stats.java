package com.bth.analysis.Stats;

public class Stats
{
  private double stddev, mean, median, variance;

  Stats(double stddev, double mean, double median, double variance)
  {
    this.stddev = stddev;
    this.mean = mean;
    this.median = median;
    this.variance = variance;
  }

  public double getVariance()
  {
    return variance;
  }

  public void setVariance(double variance)
  {
    this.variance = variance;
  }

  public double getStddev()
  {
    return stddev;
  }

  public void setStddev(double stddev)
  {
    this.stddev = stddev;
  }

  public double getMean()
  {
    return mean;
  }

  public void setMean(double mean)
  {
    this.mean = mean;
  }

  public double getMedian()
  {
    return median;
  }

  public void setMedian(double median)
  {
    this.median = median;
  }
}
