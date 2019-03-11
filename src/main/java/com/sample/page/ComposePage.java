package com.sample.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.sample.manager.Utility;

public class ComposePage extends BasePage {
  @FindBy(xpath = "//textarea[@name='to']")
  private WebElement toTextBox;

  @FindBy(xpath = "//input[@name='subjectbox']")
  private WebElement subjectTextBox;

  @FindBy(xpath = "//div[@aria-label='Message Body']")
  private WebElement messageBodyTextBox;

  @FindBy(xpath = "//div[contains(@aria-label,'Send')]")
  private WebElement sendButton;

  public ComposePage(BasePage page) {
    this.driver = page.getDriver();
    this.config = page.getConfig();
    PageFactory.initElements(driver, this);
  }

  public void inputTo(String to) {
    toTextBox.sendKeys(to);
  }

  public void inputSubject(String subject) {
    subjectTextBox.sendKeys(subject);
  }

  public void inputBody(String body) {
    messageBodyTextBox.sendKeys(body);
  }

  public InboxPage send() {
    sendButton.click();
    Utility.sleep(10);

    return new InboxPage(this);
  }
}
