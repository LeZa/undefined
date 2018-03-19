package com.build.thinking.in.java.generics;

import com.build.thinking.in.java.typeinfo.pets.*;
import java.util.*;
import com.build.thinking.in.java.net.mindview.util.*;

public class SimplerPets {
  public static void main(String[] args) {
    Map<Person, List<? extends Pet>> petPeople = New.map();
    // Rest of the code is the same...
  }
} ///:~
