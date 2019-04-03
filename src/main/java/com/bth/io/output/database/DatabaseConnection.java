package com.bth.io.output.database;

import com.bth.gui.controller.loginusers.MongoDBUser;
import com.bth.gui.controller.loginusers.SQLLoginUser;
import com.mongodb.client.MongoDatabase;
import java.sql.Connection;

public abstract class DatabaseConnection {

  protected abstract Connection connectToSql(SQLLoginUser user);

  protected abstract MongoDatabase connectToMongo(MongoDBUser user);
}
