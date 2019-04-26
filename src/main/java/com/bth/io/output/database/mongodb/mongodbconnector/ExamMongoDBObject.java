package com.bth.io.output.database.mongodb.mongodbconnector;

import com.bth.analysis.ExamAnalysis;
import com.bth.analysis.stats.StatsSchool;
import com.bth.analysis.stats.StatsTeam;
import com.bth.analysis.stats.helperobjects.RoundOffStatsQuestion;
import com.mongodb.BasicDBObject;

public class ExamMongoDBObject extends BasicDBObject {

  public static BasicDBObject toDBObject(StatsSchool school) {
    return new BasicDBObject()
        .append("name", school.getSchool())
        .append("score", school.getScore())
        .append("mean", school.getMean())
        .append("standarddev", school.getStddev())
        .append("variance", school.getVariance())
        .append("median", school.getMedian());
  }

  public static BasicDBObject toDBObject(StatsTeam team) {
    return new BasicDBObject()
        .append("name", team.getTeam())
        .append("score", team.getScore())
        .append("mean", team.getMean())
        .append("standarddev", team.getStddev())
        .append("variance", team.getVariance())
        .append("median", team.getMedian());
  }

  public static BasicDBObject toDBObject(RoundOffStatsQuestion question,
      ExamAnalysis examAnalysis) {
    int index = Integer.parseInt(question.getQuestion());
    return new BasicDBObject()
        .append("name", index)
        .append("maxScore", examAnalysis.getQuestionMaxScore(question))
        .append("mean", question.getMean())
        .append("standarddev", question.getStddev())
        .append("variance", question.getVariance())
        .append("median", question.getMedian());
  }
}
