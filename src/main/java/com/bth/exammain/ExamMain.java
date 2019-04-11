package com.bth.exammain;

public class ExamMain {

  public static void main(final String... args) {

    /*SQLLoginUser user = new SQLLoginUser("Edwin", "Edwin98".toCharArray(), "test_input",
        jdbc:mysql://localhost:8889/stats_exams?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC
        mongodb+srv://edwin-carlsson:Edwin98@examanalysiscluster-hsaye.mongodb.net/test?retryWrites=true
    FileInput fileInput = new FileInput(user);

    List<String[]> strings = fileInput.fileInputFromDatabase();*/

    java.awt.EventQueue.invokeLater(ExamMain::run);
  }

  private static void run() {
    ExamProjectHandler handler = new ExamProjectHandler();
    handler.main();
  }
}
