package com.build.thinking.in.java.generics;

import com.build.thinking.in.java.typeinfo.pets.*;
import java.util.*;
import com.build.thinking.in.java.net.mindview.util.*;

public class ExplicitTypeSpecification {
  static void f(Map<Person, List<Pet>> petPeople) {}
  public static void main(String[] args) {
    f(New.<Person, List<Pet>>map());
  }
} ///:~
