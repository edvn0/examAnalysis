package com.bth.gui.controller;

import com.bth.io.database.sql.sqlconnector.SQLConnector;
import com.bth.io.database.sql.sqlcontroller.SQLController;

import javax.swing.*;
import java.util.ArrayList;

public class GUIController {
  public static boolean dbChoice = false; // SQL if TRUE.
  private boolean isLoggedIn;

  public GUIController() {
  }

  public void setEnabledForAll(ArrayList<JComponent> component, boolean value) {
    for (JComponent c : component) {
      c.setEnabled(value);
    }
  }

  public void connectToDatabase(DatabaseLoginUser user) {
    SQLController.setDatabaseLoginUser(user);
    SQLConnector.connectToDatabase();
  }

}
