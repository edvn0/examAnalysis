package exams;

import java.util.Arrays;
import java.util.Date;

// Main object, ska representera tentorna, med vilken kod de skrevs, alla separata poäng, datum.
public class Exam
{
  private int anonymousCode;
  private int score;
  private Date date;
  private int[] separateScoresForAllQuestions;

  public Exam(int score, int[] scores, Date date, int anonymousCode)
  {
    this.anonymousCode = anonymousCode;
    this.separateScoresForAllQuestions = scores;
    this.score = score;
    this.date = date;
  }

  public Exam()
  {
  }

  @Override
  public String toString()
  {
    return "Exam{" +
        "anonymousCode=" + anonymousCode +
        ", score=" + score +
        ", date=" + date +
        ", separateScoresForAllQuestions=" + Arrays.toString(separateScoresForAllQuestions) +
        '}';
  }

  // Mutators!

  public int getScore()
  {
    return score;
  }

  public void setScore(int score)
  {
    this.score = score;
  }

  Date getDate()
  {
    return date;
  }

  public void setDate(Date date)
  {
    this.date = date;
  }

  public int[] getSeparateScoresForAllQuestions()
  {
    return separateScoresForAllQuestions;
  }

  public void setSeparateScoresForAllQuestions(int[] separateScoresForAllQuestions)
  {
    this.separateScoresForAllQuestions = separateScoresForAllQuestions;
  }

  public int getAnonymousCode()
  {
    return anonymousCode;
  }

  public void setAnonymousCode(int anonymousCode)
  {
    this.anonymousCode = anonymousCode;
  }
}
