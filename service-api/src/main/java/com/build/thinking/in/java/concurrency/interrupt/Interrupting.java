package com.build.thinking.in.java.concurrency.interrupt;

import com.build.thinking.in.java.concurrency.IOBlocked;

import java.util.concurrent.*;
import java.io.*;

import static com.build.thinking.in.java.net.mindview.util.Print.print;

class SleepBlocked implements Runnable {
  public void run() {
    try {
      TimeUnit.SECONDS.sleep(100);
    } catch(InterruptedException e) {
      print("InterruptedException");//Step 5
    }
    print("Exiting SleepBlocked.run()"); //step6
  }
}

class SynchronizedBlocked implements Runnable {
  public synchronized void f() {
    print("SynchronizedBlocked.f()..Thread.yield()");
    while(true) // Never releases lock
      Thread.yield();//  Causes the currently executing thread object to temporarily pause and allow other threads to execute.
  }
  public SynchronizedBlocked() {
    new Thread() {
      public void run() {
        f(); // Lock acquired by this thread
      }
    }.start();
  }
  public void run() {
    print("Trying to call f()");
    f();
    print("Exiting SynchronizedBlocked.run()");
  }
}

public class Interrupting {
  private static ExecutorService exec =
    Executors.newCachedThreadPool();
  static void test(Runnable r) throws InterruptedException{
    Future<?> f = exec.submit(r);
    TimeUnit.MILLISECONDS.sleep(100);
    print("Interrupting " + r.getClass().getName());// Step 1;
    print( "SleepBlocked .run is/not done...."+f.isDone()); //Step 2
    f.cancel(true); // Interrupts if running  cancel  SleepBlocked run. Step 3
    print("Interrupt sent to " + r.getClass().getName());// Step 4
    print("111111111111111111111111111111111111111111");// Step 4
  }
  public static void main(String[] args) throws Exception {
    test(new SleepBlocked());
    print("-----------------------------------------------------------");
    test(new IOBlocked(System.in));
    print("-----------------------------------------------------------");
    test(new SynchronizedBlocked());
//    TimeUnit.SECONDS.sleep(3);
    print("Aborting with System.exit(0)");
    System.exit(0); // ... since last 2 interrupts failed
  }
} /* Output: (95% match)
Interrupting SleepBlocked
InterruptedException
Exiting SleepBlocked.run()
Interrupt sent to SleepBlocked
Waiting for read():
Interrupting IOBlocked
Interrupt sent to IOBlocked
Trying to call f()
Interrupting SynchronizedBlocked
Interrupt sent to SynchronizedBlocked
Aborting with System.exit(0)
*///:~
