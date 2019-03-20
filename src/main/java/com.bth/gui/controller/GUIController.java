package com.bth.gui.controller;

import javax.swing.*;

public class GUIController
{
  private boolean isLoggedIn;

  public GUIController()
  {
  }

  public void setUp(JComponent jComponent)
  {

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
