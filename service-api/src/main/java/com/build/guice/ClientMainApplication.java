package com.build.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ClientMainApplication {

    public static void  version1(){

        Injector injector = Guice.createInjector( new GuiceInjector() ) ;
        MyApplication myApplication = injector.getInstance( MyApplication.class );
        myApplication.calculate(1,2);
    }

    public static void version2(){
            Injector injector = Guice.createInjector();
        AdditionService addService = injector.getInstance(AdditionService.class);
        addService.calculate(1,2);

    }

    public static void main( String sck[] ){

//        version1();
        version2();
    }
}
