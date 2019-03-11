package com.sample.page;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class EmailViewPage extends BasePage {

  public EmailViewPage(BasePage page) {
    this.driver = page.getDriver();
    this.config = page.getConfig();
    PageFactory.initElements(driver, this);
  }

  public void verifyFrom(String email) {
    driver.findElement(By.xpath("//span[@role='gridcell']/span[@email='" + email + "']"));
  }

  public void verifyMessageBody(String body) {
    driver.findElement(By.xpath("//div[contains(.,'" + body + "')]"));
  }
}
