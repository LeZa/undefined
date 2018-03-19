package com.build.thinking.in.java.exceptions;

import static com.build.thinking.in.java.net.mindview.util.Print.print;

class ThreeException extends Exception {}

public class FinallyWorks {
  static int count = 0;
  public static void main(String[] args) {
      se();
    while(true) {
      try {
        // Post-increment is zero first time:
        if(count++ == 0)
          throw new ThreeException();
        System.out.println("No exception");
      } catch(ThreeException e) {
        System.out.println("ThreeException");
      } finally {
        System.out.println("In finally clause");
        if(count == 2) break; // out of "while"
      }
    }
  }
  public static void se(){
        int i  = 0;
        print(i++);
        print(i);
  }
} /* Output:
ThreeException
In finally clause
No exception
In finally clause
*///:~
