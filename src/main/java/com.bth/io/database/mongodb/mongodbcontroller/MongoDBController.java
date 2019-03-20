package com.bth.io.database.mongodb.mongodbcontroller;

import com.bth.analysis.Stats.StatsSchool;
import com.bth.gui.controller.DatabaseLoginUser;
import com.bth.io.database.mongodb.mongodbconnector.ExamMongoDBObject;
import com.bth.io.database.mongodb.mongodbconnector.MongoDBConnector;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import java.util.ArrayList;
import java.util.List;

public class MongoDBController
{
  public static void setDatabaseLoginUser(DatabaseLoginUser user)
  {
    MongoDBConnector.user = user;
  }

  // TODO: Here is the problem for multithreaded access to the MongoDB database.
  public static void insertIntoMongoDatabase(MongoCollection<BasicDBObject> collection, List<StatsSchool> schoolList)
  {
    List<BasicDBObject> objects = new ArrayList<>();

    for (StatsSchool school : schoolList)
    {
      objects.add(ExamMongoDBObject.toDBObject(school));
    }

    collection.insertMany(objects);
  }
}
