package analysis.Stats;

public class StatsSchool extends Stats
{
  private String school;

  public StatsSchool(String school, double mean, double median, double stddev)
  {
    super(mean, median, stddev);
    this.school = school;
  }
}
