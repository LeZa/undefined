package com.service.api.me.base.concurrency.net;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RunnabldeT {

    class MyThread implements  Runnable{

        /** Ten ticket**/
        private  int ticketCount = 10;

        @Override
        public void run(){
            for( int i = 0; i < 20; i++ ){
                if( ticketCount > 0 ){
                     System.out.println(
                                Thread.currentThread().getName()+" sell ticket and tikcetno is :"+this.ticketCount--
                     );
                }
            }
        }
    }

    @Test
    public void sellTicket(){
        MyThread myThread = new MyThread();
        Thread thread1 = new Thread( myThread );
        Thread thread2 = new Thread( myThread );
        Thread thread3 = new Thread( myThread );
        thread1.start();
        thread2.start();
        thread3.start();
    }
    /***********/

    class BlockThread
        implements  Runnable{

        private BlockingQueue<A> blockingQueue;

        private volatile boolean isRunning = false;

        public BlockThread( BlockingQueue<A> blockingQueue){
            this.blockingQueue = blockingQueue;
        }

        public boolean pushA(A a){

            if( isRunning ){
                synchronized (blockingQueue) {
                    boolean ret = blockingQueue.add(a);
                    if(ret){
                        blockingQueue.notifyAll();
                    }
                    return ret;
                }
            }
            return false;
        }


        @Override
        public void run(){
            isRunning = true;
            try {
                while(true) {
                    A msg = null;
                    synchronized (blockingQueue) {
                        msg = blockingQueue.poll();
                        if(msg == null){
                            blockingQueue.wait();
                            continue;
                        }
                    }
                    msg.printStr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    class A{

        private String str;

        public A( String str ){
                this.str = str;
        }

        public void printStr(){
            System.out.println( str );
        }
    }

    @Test
    public void blockQueue(){
        /** Add 100 str**/
        BlockingQueue<A> blockingQueue = new LinkedBlockingQueue<A>();
        BlockThread blockThread = new BlockThread( blockingQueue);
        Thread thread = new Thread( blockThread);
        thread.start();
        for( int i = 1; i < 100; i++ ){
                blockThread.pushA(new A( String.valueOf( i) ) );
        }
    }


}



