package com.sample.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InboxPage extends BasePage {
  @FindBy(xpath = "//div[@role='button'][contains(.,'Compose')]")
  private WebElement composeButton;

  public InboxPage(BasePage page) {
    this.driver = page.getDriver();
    this.config = page.getConfig();
    PageFactory.initElements(driver, this);
  }

  public ComposePage selectCompose() {
    composeButton.click();

    return new ComposePage(this);
  }

  public EmailViewPage openEmailBySubject(String subject) {
    this.returnToInboxPage();
    driver
        .findElement(
            By.xpath("//div[@role='link']/div/div/span/span[contains(.,'" + subject + "')]"))
        .click();
    return new EmailViewPage(this);
  }

  public void selectAllEmail() {
    Actions action = new Actions(driver);
    WebElement checkbox =
        driver.findElement(By.xpath("//div[@role='button'][@aria-label='Select']/div/span[@role='checkbox']"));
    action.moveToElement(checkbox).build().perform();
    checkbox.click();
  }

  public void selectDelete() {
    Actions action = new Actions(driver);
    WebElement button =
        driver.findElement(By.xpath("//div[@role='button'][@aria-label='Delete']/div"));
    action.moveToElement(button).build().perform();
    button.click();
  }
}
