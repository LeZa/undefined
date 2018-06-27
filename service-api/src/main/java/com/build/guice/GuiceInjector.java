package com.build.guice;

import com.google.inject.AbstractModule;

public class GuiceInjector
    extends AbstractModule{

    @Override
    protected  void configure(){
        bind( ArithmeticService.class ).to( AdditionService.class );
    }
}
