package com.bth.analysis.Stats.helperobjects;

public class RoundOffStatsQuestion extends RoundOffStats
{
  private String question;

  public RoundOffStatsQuestion(double mean, double median, double stddev, double variance, double numOfDec, String question)
  {
    super(mean, median, stddev, variance, numOfDec);
    this.question = question;
  }

  public String getQuestion()
  {
    return question;
  }

  @Override
  public String toString()
  {
    return
        "Question: " + question + "\n" +
            "Mean: [" + getMean() + "]\n" +
            "Median: [" + getMedian() + "]\n" +
            "Standard: [" + getStddev() + "]\n" +
            "Variance: [" + getVariance() + ']';
  }
}
