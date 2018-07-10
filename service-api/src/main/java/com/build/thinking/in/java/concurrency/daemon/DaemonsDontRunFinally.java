package com.build.thinking.in.java.concurrency.daemon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.build.thinking.in.java.net.mindview.util.Print.print;

class ADaemon implements Runnable {

  public void run() {
    try {
      print("Starting ADaemon");
      TimeUnit.SECONDS.sleep(1);
    } catch(InterruptedException e) {
      print("Exiting via InterruptedException");
    } finally {
      print("This should always run?");
    }
  }

}


public class DaemonsDontRunFinally {

  public static void main(String[] args){
        Thread t = new Thread(new ADaemon());
        t.setDaemon(true);
        t.start();
//    ExecutorService exec = Executors.newCachedThreadPool();
//    exec.execute( new ADaemon1());
//    exec.shutdownNow();
  }
} /* Output:
Starting ADaemon
*///:~
