package com.build.guice;

public class DivisionService
        implements  ArithmeticService{

    @Override
    public void calculate(int a, int b) {
        System.out.println("Divisiton of " + a + " and " + b + " is: " + (a / b));
    }
}
