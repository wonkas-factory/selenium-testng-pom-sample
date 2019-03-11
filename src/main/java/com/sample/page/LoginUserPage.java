package com.sample.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginUserPage extends BasePage {
  @FindBy(id = "identifierId")
  private WebElement userName;

  @FindBy(id = "identifierNext")
  private WebElement nextButton;

  public LoginUserPage(String user) {
    PageFactory.initElements(intitialize(user), this);
  }

  public LoginUserPage(BasePage page) {
    this.driver = page.getDriver();
    this.config = page.getConfig();
    PageFactory.initElements(driver, this);
  }

  public LoginPasswordPage enterLogin(String name) {
    userName.sendKeys(name);
    nextButton.click();
    return new LoginPasswordPage(this);
  }
}
