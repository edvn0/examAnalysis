package com.bth.gui.controller;

import com.bth.io.database.sql.sqlconnector.SQLConnector;
import com.bth.io.database.sql.sqlcontroller.SQLController;

import javax.swing.*;
import java.util.ArrayList;

public class GUIController
{
  private boolean isLoggedIn;

  public GUIController()
  {
  }

  public void setEnabledForAll(ArrayList<JComponent> component, boolean value)
  {
    for (JComponent c : component)
    {
      c.setEnabled(value);
    }
  }

  public void connectToDatabase(DatabaseLoginUser user)
  {
    SQLController.setDatabaseLoginUser(user);
    SQLConnector.connectToDatabase();
  }

  public void setLoggedIn(boolean loggedIn)
  {
    isLoggedIn = loggedIn;
  }

  public boolean isLoggedIn()
  {
    return isLoggedIn;
  }
}
