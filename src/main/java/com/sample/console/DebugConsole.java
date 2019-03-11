package com.sample.console;

import java.io.InputStreamReader;
import java.io.Reader;

import bsh.Interpreter;

public class DebugConsole {

  public static void main(String[] args) {
    final Reader in = new InputStreamReader(System.in);
    final Interpreter interpreter = new Interpreter(in, System.out, System.err, true);

    // Imports
    interpreter.getNameSpace().importPackage("com.sample.logic");
    interpreter.getNameSpace().importPackage("com.sample.page");
    interpreter.getNameSpace().importPackage("com.sample.manager");
    interpreter.getNameSpace().importPackage("org.openqa.selenium");
    interpreter.getNameSpace().importPackage("org.openqa.selenium.support");

    System.out.println("Initialize user driver by:");
    System.out.println("alice = (InboxPage) LoginLogic.login(new LoginUserPage(\"Alice\"));");
    System.out.println("driver = alice.getDriver();");
    interpreter.run();
  }
}
