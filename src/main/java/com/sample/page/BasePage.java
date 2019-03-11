package com.sample.page;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.sample.manager.TestData;

public class BasePage {
  protected WebDriver driver;
  protected Map<String, String> config;

  protected WebDriver intitialize(String user) {
    TestData testData = new TestData();
    config = testData.getUserConfig(user);

    String url = config.get("url");
    String browser = config.get("browser");

    if (browser.equalsIgnoreCase("Chrome")) {
      driver = new ChromeDriver();
    } else if (browser.equalsIgnoreCase("Safari")) {
      driver = new SafariDriver();
    } else if (browser.equalsIgnoreCase("Firefox")) {
      driver = new FirefoxDriver();
    } else if (browser.equalsIgnoreCase("Edge")) {
      driver = new EdgeDriver();
    } else if (browser.equalsIgnoreCase("Firefox")) {
      driver = new InternetExplorerDriver();
    } else {
      driver = new ChromeDriver();
    }

    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    driver.manage().deleteAllCookies();
    driver.get(url);

    return driver;
  }

  protected WebDriver getDriver() {
    return this.driver;
  }

  protected Map<String, String> getConfig() {
    return this.config;
  }

  public String getUserProperty(String key) {
    return config.get(key);
  }

  public InboxPage returnToInboxPage() {
    driver.findElement(By.linkText("Inbox")).click();
    return new InboxPage(this);
  }

  public void quit() {
    driver.quit();
  }
}
