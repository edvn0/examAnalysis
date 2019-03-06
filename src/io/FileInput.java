package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileInput
{
  private Scanner scanner;

  FileInput(String dir) throws FileNotFoundException
  {
    scanner = new Scanner(new File(dir));
  }

  List<String[]> fileInput()
  {
    List<String[]> values = new ArrayList<>();
    while (scanner.hasNext())
    {
      values.add(scanner.nextLine().split(","));
    }
    return values;
  }

  int getIndex(boolean startorend, List<String[]> data)
  {
    String question = startorend ? "Fråga 1 Poäng".toLowerCase().trim() : "Fråga 14 Poäng".toLowerCase().trim();
    int length = data.get(0).length;

    for (int j = 0; j < length; j++)
    {
      String string = data.get(0)[j].trim().toLowerCase();
      if (startorend)
      {
        if (string.equals(question))
        {
          return j;
        }
      } else
      {
        if (string.equals(question))
        {
          return j + 1;
        }
      }
    }
    return -1;
  }
}
