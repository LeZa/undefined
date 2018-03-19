package com.build.thinking.in.java.annotations;


import com.build.thinking.in.java.net.mindview.atunit.Test;

public class Testable {
  public void execute() {
    System.out.println("Executing..");
  }
  @Test
  void testExecute() { execute(); }
} ///:~
