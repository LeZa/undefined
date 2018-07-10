package com.build.thinking.in.java.reusing;

import static com.build.thinking.in.java.net.mindview.util.Print.print;

class SmallBrain {}

final class Dinosaur {
  int i = 7;
  int j = 1;
  SmallBrain x = new SmallBrain();
  void f() {
    print(i);
  }
}

//! class Further extends Dinosaur {}
// error: Cannot extend final class 'Dinosaur'

public class Jurassic {
  public static void main(String[] args) {
    Dinosaur n = new Dinosaur();
    n.f();
    n.i = 40;
    n.j++;
    n.f();
  }
} ///:~
