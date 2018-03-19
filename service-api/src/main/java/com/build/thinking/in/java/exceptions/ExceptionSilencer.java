package com.build.thinking.in.java.exceptions;

import static com.build.thinking.in.java.net.mindview.util.Print.print;

/**
 * Description RunntimeException 测试
 */
public class ExceptionSilencer {
  public static void main(String[] args) {
    try {
      throw new RuntimeException();
    } finally {
      // Using 'return' inside the finally block
      // will silence any thrown exception.
        print("Silencer exception  runtimeexception");
      return;
    }
  }
} ///:~
