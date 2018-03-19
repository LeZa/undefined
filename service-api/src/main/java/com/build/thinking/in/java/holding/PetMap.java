package com.build.thinking.in.java.holding;

import com.build.thinking.in.java.typeinfo.pets.Cat;
import com.build.thinking.in.java.typeinfo.pets.Dog;
import com.build.thinking.in.java.typeinfo.pets.Hamster;
import com.build.thinking.in.java.typeinfo.pets.Pet;

import java.util.*;

import static com.build.thinking.in.java.net.mindview.util.Print.print;


public class PetMap {
  public static void main(String[] args) {
    Map<String,Pet> petMap = new HashMap<String,Pet>();
    petMap.put("My Cat", new Cat("Molly"));
    petMap.put("My Dog", new Dog("Ginger"));
    petMap.put("My Hamster", new Hamster("Bosco"));
    print(petMap);
    Pet dog = petMap.get("My Dog");
    print(dog);
    print(petMap.containsKey("My Dog"));
    print(petMap.containsValue(dog));
  }
} /* Output:
{My Cat=Cat Molly, My Hamster=Hamster Bosco, My Dog=Dog Ginger}
Dog Ginger
true
true
*///:~
