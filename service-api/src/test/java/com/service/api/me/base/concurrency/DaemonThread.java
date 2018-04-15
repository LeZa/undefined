package com.service.api.me.base.concurrency;

public class DaemonThread
    implements  Runnable{


    @Override
    public void run(){
        for( int  i = 0; i < Integer.MAX_VALUE ; i++ ){
            System.out.println( i );
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main( String sck[] ){
            Thread thread = new Thread( new DaemonThread());
//            thread.setDaemon( true );
            thread.start();
        try {
            thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
