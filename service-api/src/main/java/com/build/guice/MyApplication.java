package com.build.guice;

import javax.inject.Inject;

public class MyApplication {

    private ArithmeticService arithmeticService;

    //Guice框架既支持基于构造方法的注入方式，也支持基于setter的注入方式
    @Inject
    public void setService( ArithmeticService service){
        this.arithmeticService = service;
    }

    public void calculate(int a, int b) {
        arithmeticService.calculate(a, b);
    }
}
