//: net/mindview/util/DaemonThreadFactory.java
package com.build.thinking.in.java.concurrency.daemon;
import java.util.concurrent.*;

public class DaemonThreadFactory implements ThreadFactory {
  public Thread newThread(Runnable r) {
    Thread t = new Thread(r);
    t.setDaemon(true);
    return t;
  }
} ///:~
