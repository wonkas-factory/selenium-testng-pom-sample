package com.sample.page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.sample.manager.Utility;

public class LoginPasswordPage extends BasePage {
  @FindBy(css = "input[name='password']")
  private WebElement passwordBox;

  public LoginPasswordPage(BasePage page) {
    this.driver = page.getDriver();
    this.config = page.getConfig();
    PageFactory.initElements(driver, this);
  }

  public InboxPage enterPassword(String password) {
    passwordBox.sendKeys(password);
    passwordBox.sendKeys(Keys.ENTER);
    Utility.sleep(5);
    return new InboxPage(this);
  }
}
