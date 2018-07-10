package com.service.api.me.base;


public class TryModule {

    public  static String tryModule(){
         TryModule tryModule = new TryModule();
        try{
            tryModule.key(1);
        }catch( Exception exp ){

        }

        try{

            tryModule.key(2);
            return "bb";
        }catch( Exception exp ){

        }

        return "aa";
    }

    public void key( int i) throws Exception{

        if( i == 1  ){
            throw  new Exception( "My Exception");
        }
        if ( i == 2 ){

        }
    }

    public static void main( String sck[] ){
       System.out.println( tryModule() );
    }


}
