package analysis.Stats;

public class Stats
{
  double mean, median, stddev;

  Stats(double mean, double median, double stddev)
  {
    this.mean = mean;
    this.median = median;
    this.stddev = stddev;
  }

  @Override
  public String toString()
  {
    return "Stats{" +
        "mean=" + mean +
        ", median=" + median +
        ", stddev=" + stddev +
        '}';
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

  public double getStddev()
  {
    return stddev;
  }

  public void setStddev(double stddev)
  {
    this.stddev = stddev;
  }
}
