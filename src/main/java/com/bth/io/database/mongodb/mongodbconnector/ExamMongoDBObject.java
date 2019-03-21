package com.bth.io.database.mongodb.mongodbconnector;

import com.bth.analysis.Stats.StatsSchool;
import com.mongodb.BasicDBObject;

public class ExamMongoDBObject extends BasicDBObject {

  public static BasicDBObject toDBObject(StatsSchool school) {
    return new BasicDBObject().append("name", school.getSchool()).append("score", school.getScore())
        .append("mean", school.getMean()).append("standarddev", school.getStddev())
        .append("variance", school.getVariance()).append("median", school.getMedian());
  }
}
