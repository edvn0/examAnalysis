package com.bth.io.output.database;

import com.bth.gui.controller.loginusers.DatabaseLoginUser;

public abstract class DatabaseConnection implements ConnectInterface {

  public abstract void connect(DatabaseLoginUser u);

  public abstract void disconnect();

  public abstract boolean validateDatabase(DatabaseLoginUser user);

  public abstract boolean isUserValidated(DatabaseLoginUser user);

  protected abstract boolean isConnected();
}
