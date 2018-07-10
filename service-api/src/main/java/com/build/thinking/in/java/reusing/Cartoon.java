package com.build.thinking.in.java.reusing;

import static com.build.thinking.in.java.net.mindview.util.Print.print;

class Art {
  Art() { print("Art constructor"); }
  Art(String s){
    print("Art a parameter constructor");
  }
}


class Drawing extends Art {
  Drawing() {
    print("Drawing constructor");
  }

  Drawing(String s){
    print("Drawing a parameter constructor");
  }

  public void printVoid(){
    print("Drawing print method.");
  }
}

public class Cartoon extends Drawing {


  public Cartoon() {
    print("Cartoon constructor");
  }
  public Cartoon( String s){
    print("Cartoon a parameter constructor");
  }

  @Override
  public void printVoid(){
    print("Cartoon print method.");
  }

  public static void main(String[] args) {
    Cartoon x = new Cartoon();
    print("---------------------------------------");
    Cartoon y = new Cartoon("y");
//    x.printVoid();
  }
} /* Output:
Art constructor
Drawing constructor
Cartoon constructor
*///:~
