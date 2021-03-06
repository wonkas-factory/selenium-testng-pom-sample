package com.sample;

import org.testng.annotations.Test;

import com.sample.logic.ComposeLogic;
import com.sample.logic.InboxLogic;
import com.sample.logic.LoginLogic;
import com.sample.manager.GmailAPI;
import com.sample.manager.TestData;
import com.sample.manager.Utility;
import com.sample.page.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

public class EmailTests {
  private TestData testData;
  private BasePage alice, bob;
  private GmailAPI aliceService, bobService;

  @BeforeClass
  public void beforeClass() throws GeneralSecurityException, IOException {
    testData = new TestData();
    alice = (InboxPage) LoginLogic.login(new LoginUserPage("Alice"));
    bob = (InboxPage) LoginLogic.login(new LoginUserPage("Bob"));
    
    // Use Gmail API to delete all messages
    aliceService = new GmailAPI(alice);
    bobService = new GmailAPI(bob);
    aliceService.deleteAllMessages("me");
    bobService.deleteAllMessages("me");
  }

  @AfterClass
  public void afterClass() {
    Utility.sleep(5);
    ((BasePage) alice).quit();
    ((BasePage) bob).quit();
  }

  @BeforeMethod
  public void beforeMethod() {
    alice = alice.returnToInboxPage();
    bob = bob.returnToInboxPage();
  }

  @AfterMethod
  public void afterMethod(ITestResult result) throws GeneralSecurityException, IOException {
    if (result.getStatus() == ITestResult.FAILURE) {
      Utility.takeScreenshot(alice, result);
      Utility.takeScreenshot(bob, result);
      afterClass();
      beforeClass();
    } else {
      alice = alice.returnToInboxPage();
      InboxLogic.deleteAllEmails(alice);
      bob = bob.returnToInboxPage();
      InboxLogic.deleteAllEmails(bob);
    }
  }

  @Test
  public void sendMail() {
    // Test Data - Strings
    String subject = testData.company().buzzword();
    String body = Utility.safeQuotes(testData.company().catchPhrase());

    // Alice - compose email
    ComposeLogic.sendEmail(alice, bob, subject, body);

    // Bob - verify email
    bob = ((InboxPage) bob).openEmailBySubject(subject);
    ((EmailViewPage) bob).verifyFrom(alice.getUserProperty("email_login"));
    ((EmailViewPage) bob).verifyMessageBody(body);
  }

  @Test
  public void sendMailEmptyBody() {
    // Test Data - Strings
    String subject = testData.company().buzzword();
    String body = "";

    // Alice - compose email
    ComposeLogic.sendEmail(alice, bob, subject, body);

    // Bob - verify email
    bob = ((InboxPage) bob).openEmailBySubject(subject);
    ((EmailViewPage) bob).verifyFrom(alice.getUserProperty("email_login"));
    ((EmailViewPage) bob).verifyMessageBody(body);
  }
}
