package com.bth.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileInput {

  private Scanner scanner;

  FileInput(String dir) throws FileNotFoundException {
    scanner = new Scanner(new File(dir));
  }

  List<String[]> fileInput() {
    List<String[]> values = new ArrayList<>();
    while (scanner.hasNext()) {
      values.add(scanner.nextLine().split(","));
    }
    return values;
  }

  /***
   * Returns the index of where the questions start or end.
   * @param startorend <code>true</code> if where they start in the array, <code>false</code> otherwise
   * @param data the input <code>List<String[]></code> from the input tsv file.
   * @return <code>int</code>
   */
  int getIndex(boolean startorend, List<String[]> data) {
    String question =
        startorend ? "Fråga 1 Poäng".toLowerCase().trim() : "Fråga 14 Poäng".toLowerCase().trim();
    String[] inData = data.get(0);
    int length = inData.length;

    for (int j = 0; j < length; j++) {
      String string = inData[j].trim().toLowerCase();
      if (string.equals(question)) {
        return startorend ? j : j + 1;
      }
    }
    return -1;
  }
}
