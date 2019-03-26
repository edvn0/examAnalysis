package com.bth.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader extends Properties {

  public PropertiesReader(String dir) throws IOException {
    super();
    FileInputStream inputStream;
    inputStream = new FileInputStream(dir);
    this.load(inputStream);
    inputStream.close();
  }

  @Override
  public String getProperty(String key) {
    return super.getProperty(key);
  }
}
