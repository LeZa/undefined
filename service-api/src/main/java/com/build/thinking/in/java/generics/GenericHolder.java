package com.build.thinking.in.java.generics;

import static com.build.thinking.in.java.net.mindview.util.Print.print;

public class GenericHolder<T> {
  private T obj;
  public void set(T obj) { this.obj = obj; }
  public T get() { return obj; }
  public static void main(String[] args) {
    GenericHolder<String> holder =
      new GenericHolder<String>();
    holder.set("Item");
    String s = holder.get();
    print( s );
  }
} ///:~
