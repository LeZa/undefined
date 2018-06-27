package com.build.guice;

public class AdditionService
    implements  ArithmeticService{

    @Override
    public void calculate(int a, int b) {
        System.out.println("Addition of " + a + " and " + b + " is: " + (a + b));
    }
}
