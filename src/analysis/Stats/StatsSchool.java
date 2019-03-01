package analysis.Stats;

public class StatsSchool extends Stats
{
  private String school;
  Stats[] stats;

  public StatsSchool(String school, double mean, double median, double stddev, Stats[] stats)
  {
    super(mean, median, stddev);
    this.stats = stats;
    this.school = school;
  }
}
