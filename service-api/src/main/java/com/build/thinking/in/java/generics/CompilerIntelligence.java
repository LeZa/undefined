package com.build.thinking.in.java.generics;

import java.util.*;

import static com.build.thinking.in.java.net.mindview.util.Print.print;

public class CompilerIntelligence {
  public static void main(String[] args) {
    List<? extends Fruit> flist =
      Arrays.asList(new Apple());
//    Apple a = (Apple)flist.get(0); // No warning
      print( flist.get(0) );
      print( flist.contains( new Fruit() ));
    print( flist.contains(new Apple()) ); // Argument is 'Object'
    print( flist.indexOf(new Apple()) ); // Argument is 'Object'
  }
} ///:~
