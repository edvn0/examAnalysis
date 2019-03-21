package com.bth.io.database.mongodb.mongodbcontroller;

import com.bth.analysis.Stats.StatsSchool;
import com.bth.analysis.Stats.StatsTeam;
import com.bth.analysis.Stats.helperobjects.RoundOffStatsQuestion;
import com.bth.gui.controller.DatabaseLoginUser;
import com.bth.io.database.mongodb.mongodbconnector.ExamMongoDBObject;
import com.bth.io.database.mongodb.mongodbconnector.MongoDBConnector;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import java.util.ArrayList;
import java.util.List;

public class MongoDBController {
  public static void setDatabaseLoginUser(DatabaseLoginUser user) {
    MongoDBConnector.user = user;
  }

  public static void insertIntoMongoDatabase(MongoCollection<BasicDBObject> collection,
                                             List<StatsSchool> schoolList,
                                             List<StatsTeam> teamList,
                                             List<RoundOffStatsQuestion> questionList) {
    boolean schoolListNull = schoolList == null;
    boolean teamListNull = teamList == null;
    boolean questionListNull = questionList == null;
    boolean schoolTeamListNull = schoolListNull && teamListNull && !questionListNull; // => we will insert questions
    boolean schoolQuestionListNull = schoolListNull && questionListNull && !teamListNull; // => we will insert teams
    boolean teamQuestionNull = teamListNull && questionListNull && !schoolListNull; // => we will insert schools

    List<BasicDBObject> objects = new ArrayList<>();

    if (teamQuestionNull) {
      for (StatsSchool school : schoolList) {
        objects.add(ExamMongoDBObject.toDBObject(school));
      }
    } else if (schoolQuestionListNull) {
      for (StatsTeam team : teamList) {
        objects.add(ExamMongoDBObject.toDBObject(team));
      }
    } else if (schoolTeamListNull) {
      for (RoundOffStatsQuestion question : questionList) {
        objects.add(ExamMongoDBObject.toDBObject(question));
      }
    }
    collection.insertMany(objects);
  }
}
