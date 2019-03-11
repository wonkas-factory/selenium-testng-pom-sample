package com.sample.manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;

import com.sample.page.BasePage;

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

  public static void takeScreenshot(BasePage user, ITestResult result) {
    String path = Paths.get(".").toAbsolutePath().normalize().toString() + "/screenshot/";
    String fileName =
        user.getUserProperty("key")
            + "_"
            + result.getMethod().getMethodName()
            + "_"
            + System.currentTimeMillis()
            + ".png";
    File scrFile = ((TakesScreenshot) user.getDriver()).getScreenshotAs(OutputType.FILE);
    try {
      FileUtils.copyFile(scrFile, new File(path + fileName));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
