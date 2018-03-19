package com.build.thinking.in.java.interfaces.interfaceprocessor;

import static com.build.thinking.in.java.net.mindview.util.Print.print;


public class Apply {
  public static void process(Processor p, Object s) {
    print("Using Processor " + p.name());
    print(p.process(s));
  }
} ///:~
