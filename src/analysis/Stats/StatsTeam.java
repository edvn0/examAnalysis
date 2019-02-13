package analysis.Stats;

public class StatsTeam extends Stats
{
  private String team;

  public StatsTeam(String team, double mean, double median, double stddev)
  {
    super(mean, median, stddev);
    this.team = team;
  }
}
