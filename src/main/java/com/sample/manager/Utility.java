package com.sample.manager;

public class Utility {
  public static void sleep(int seconds) {
    try {
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static String safeQuotes(String input) {
    return input.replaceAll("\"", "\\\\\"").replaceAll("\'", "\\\\\'");
  }
}
