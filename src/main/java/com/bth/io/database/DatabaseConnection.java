package com.bth.io.database;

import com.bth.gui.controller.DatabaseLoginUser;
import com.mongodb.client.MongoDatabase;
import java.sql.Connection;

public abstract class DatabaseConnection {

  protected abstract Connection connectToSql(DatabaseLoginUser user);

  protected abstract MongoDatabase connectToMongo(DatabaseLoginUser user);
}
