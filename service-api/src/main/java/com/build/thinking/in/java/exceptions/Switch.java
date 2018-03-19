package com.build.thinking.in.java.exceptions;

import static com.build.thinking.in.java.net.mindview.util.Print.print;

public class Switch {
  private boolean state = false;
  public boolean read() { return state; }
  public void on() { state = true; print(this); }
  public void off() { state = false; print(this); }
  public String toString() { return state ? "on" : "off"; }
} ///:~
