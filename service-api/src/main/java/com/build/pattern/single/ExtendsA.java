package com.build.pattern.single;

public class ExtendsA extends A {

    public ExtendsA( String str ){
        super(str);
    }

    public void printThisStr(){
        System.out.println(str);
    }
}
