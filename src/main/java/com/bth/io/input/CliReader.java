/*
 * Copyright (c) 2018-2019 Edwin Carlsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.bth.io.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CliReader {

  public List<String> readInputInformationDatabase(boolean databaseType) {
    // mongo connector (collections included), sql connector (tables included).
    List<String> strings = new ArrayList<>();
    if (databaseType) { // True => SQL.
      System.out.println("For the SQL connection, you need to input: "
          + "\n1: The full connector name,"
          + "\n2: The database name,"
          + "\n3: The school table name,"
          + "\n4: The team table name,"
          + "\n5: The question table name,"
          + "\n6: The username,"
          + "\n7: The password"
          + "\nType exit when you are done.");
      Scanner scanner = new Scanner(System.in);
      while (true) {
        String input = scanner.nextLine();
        if (input.equals("exit")) {
          break;
        } else {
          strings.add(input);
        }
      }
    } else { // False => mongodb.
      System.out.println("For the MongoDB connection, you need to input:"
          + "\n1: The full connector name,"
          + "\n2: The database name,"
          + "\n3: The school collection name,"
          + "\n4: The team collection name,"
          + "\n5: The question collection name,"
          + "\n6: The username,"
          + "\n7: The password."
          + "\nType exit when you are done.");
      Scanner scanner = new Scanner(System.in);
      while (true) {
        String input = scanner.nextLine();
        if (input.equals("exit")) {
          break;
        } else {
          strings.add(input);
        }
      }
    }
    return cleanInput(strings);
  }

  /***
   * This method will return all relevant information for the inputs from the database,
   * login-info etc.
   * @return list of strings.
   */
  public List<String> readInputInformationFromCli() {
    List<String> strings = new ArrayList<>();
    System.out.println(
        "You will now be asked to enter information regarding your connections. Please enter the following:"
            + "\n1: your connector string (jdbc://...)."
            + "\n2: your username for the SQL database."
            + "\n3: your password for the SQL database."
            + "\n4: your table name for the input files in the database."
            + "\nType exit when you are done.");
    Scanner scanner = new Scanner(System.in);
    while (true) {
      String input = scanner.nextLine();

      if (input.equals("exit")) {
        break;
      } else {
        System.out.println(input);
        strings.add(input);
      }
    }
    return cleanInput(strings);
  }

  private List<String> cleanInput(List<String> strings) {
    int[] indices = new int[strings.size()];
    int k = 0;
    for (String s : strings) {
      if (s.equals("") || s.equals(" ")) {
        indices[k++] = strings.indexOf(s);
      }
    }

    for (int index : indices) {
      if (index != 0) {
        strings.remove(index);
      }
    }

    System.out.println("Here");
    strings.forEach(System.out::println);
    System.out.println("end here");
    return strings;
  }

  public List<String> readInputAboutOutput() {
    // directory input etc..
    System.out.println(
        "You will now be asked to enter information regarding your directory. Please enter in following format:"
            + "\n/Users/<name>/.../output.\n"
            + "\n1: the full directory for your outputs."
            + "\nType exit when you are done.");

    List<String> strings = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    while (true) {
      String input = scanner.nextLine();
      if (input.equals("exit")) {
        break;
      } else {
        strings.add(input);
      }
    }
    return cleanInput(strings);
  }

}
