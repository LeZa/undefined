package com.build.guice;

public class SubtractionService
    implements  ArithmeticService{

    @Override
    public void calculate(int a, int b) {
        System.out.println("Subtraction of " + a + " and " + b + " is: " + (a - b));
    }
}
