package com.sample.logic;

import com.sample.page.BasePage;
import com.sample.page.InboxPage;
import com.sample.page.LoginPasswordPage;
import com.sample.page.LoginUserPage;

public class LoginLogic {
  public static InboxPage login(BasePage user) {
    String username = user.getUserProperty("email_login");
    String password = user.getUserProperty("email_password");

    user = ((LoginUserPage) user).enterLogin(username);
    return ((LoginPasswordPage) user).enterPassword(password);
  }
}
