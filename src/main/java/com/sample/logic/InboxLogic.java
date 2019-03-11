package com.sample.logic;

import com.sample.page.BasePage;
import com.sample.page.InboxPage;

public class InboxLogic {
	public static void deleteAllEmails(BasePage user) {
		try {
			((InboxPage) user).selectAllEmail();
		    ((InboxPage) user).selectDelete();
		} catch (Exception e) {
			System.out.println("Warning: Possibly no emails to delete. ");
		}
		
	}
}
