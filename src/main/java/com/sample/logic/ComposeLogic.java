package com.sample.logic;

import com.sample.page.BasePage;
import com.sample.page.ComposePage;
import com.sample.page.InboxPage;

public class ComposeLogic {
  public static void sendEmail(BasePage user1, BasePage user2, String subject, String body) {
    user1 = ((InboxPage) user1).selectCompose();
    ((ComposePage) user1).inputTo(user2.getUserProperty("email_login"));
    ((ComposePage) user1).inputSubject(subject);
    ((ComposePage) user1).inputBody(body);
    user1 = ((ComposePage) user1).send();
  }
}
