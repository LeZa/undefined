package com.build.guice;

public class MultiplicationService
    implements  ArithmeticService{

    @Override
    public void calculate(int a, int b) {
        System.out.println("Multiplication of " + a + " and " + b + " is: " + (a * b));
    }
}
