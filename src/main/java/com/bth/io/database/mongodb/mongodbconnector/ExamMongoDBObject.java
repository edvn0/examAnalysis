package com.bth.io.database.mongodb.mongodbconnector;

import com.bth.analysis.Stats.StatsSchool;
import com.bth.analysis.Stats.StatsTeam;
import com.bth.analysis.Stats.helperobjects.RoundOffStatsQuestion;
import com.bth.io.ExamInput;
import com.mongodb.BasicDBObject;

public class ExamMongoDBObject extends BasicDBObject {

  public static BasicDBObject toDBObject(StatsSchool school) {
    return new BasicDBObject().append("name", school.getSchool()).append("score", school.getScore())
        .append("mean", school.getMean()).append("standarddev", school.getStddev())
        .append("variance", school.getVariance()).append("median", school.getMedian());
  }

  public static BasicDBObject toDBObject(StatsTeam team) {
    return new BasicDBObject().append("name", team.getTeam()).append("score", team.getScore())
        .append("mean", team.getMean()).append("standarddev", team.getStddev())
        .append("variance", team.getVariance()).append("median", team.getMedian());
  }

  public static BasicDBObject toDBObject(RoundOffStatsQuestion question) {
    int index = Integer.parseInt(question.getQuestion()) + 1;
    String q = String.valueOf(index);
    return new BasicDBObject().append("name", index).append("maxScore", ExamInput.getMaxScore(q))
        .append("mean", question.getMean()).append("standarddev", question.getStddev())
        .append("variance", question.getVariance()).append("median", question.getMedian());
  }
}
