//: access/Cake.java
// Accesses a class in a separate compilation unit.
package com.build.thinking.in.java.access;

class Cake {
  public static void main(String[] args) {
    Pie x = new Pie();
    x.f();
  }
} /* Output:
Pie.f()
*///:~
