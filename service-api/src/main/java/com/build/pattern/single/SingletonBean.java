package com.build.pattern.single;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SingletonBean {

    private String str;

    @Inject
    public SingletonBean( String str ){
        this.str = str;
    }

    public void printThisStr(){
        System.out.println( "Print this str is:"+ str);
    }
}
