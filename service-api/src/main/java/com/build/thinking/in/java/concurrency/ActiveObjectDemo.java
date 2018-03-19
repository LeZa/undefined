package com.build.thinking.in.java.concurrency;

import java.util.concurrent.*;
import java.util.*;

import static com.build.thinking.in.java.net.mindview.util.Print.print;

class ThreadFactoryConfig implements ThreadFactory {

    @Override
    public Thread newThread(Runnable runnable) {
        return null;
    }
}


public class ActiveObjectDemo {
  private ExecutorService ex =
  // singleThreadExecutor.executor();
    Executors.newSingleThreadExecutor();
    /*// fixedThreadPool(3).executor();
   Executors.newFixedThreadPool(3);*/
    /*// cachedThreadPool().executor()
    Executors.newCachedThreadPool();*/
    /*//scheduledThreadPool(3);
    Executors.newScheduledThreadPool(3);*/

  private Random rand = new Random(47);
  // Insert a random delay to produce the effect
  // of a calculation time:
  private void pause(int factor) {
    try {
      TimeUnit.MILLISECONDS.sleep(
        100 + rand.nextInt(factor));
    } catch(InterruptedException e) {
      print("sleep() interrupted");
    }
  }
  public Future<Integer>
  calculateInt(final int x, final int y) {
    return ex.submit(new Callable<Integer>() {
      public Integer call() {
        print("Starting  calculate intMethod..." + x + " + " + y);
        pause(500);
        return x + y;
      }
    });
  }
  public Future<Float>
  calculateFloat(final float x, final float y) {
    return ex.submit(new Callable<Float>() {
      public Float call() {
        print("Starting  calculate floatMethod..." + x + " + " + y);
        pause(2000);
        return x + y;
      }
    });
  }
  public void shutdown() { ex.shutdown(); }
  public static void main(String[] args) {
        ActiveObjectDemo d1 = new ActiveObjectDemo();
        // Prevents ConcurrentModificationException:
        List<Future<?>> results =
          new CopyOnWriteArrayList<Future<?>>();
        for(float f = 0.0f; f < 1.0f; f += 0.2f)
          results.add(d1.calculateFloat(f, f));
        for(int i = 0; i < 5; i++)
          results.add(d1.calculateInt(i, i));
        print("All asynch calls made");
        while(results.size() > 0) {
          for(Future<?> f : results)
            if(f.isDone()) {
              try {
                print("Execute get method value is..."+f.get());
              } catch(Exception e) {
                throw new RuntimeException(e);
              }
              results.remove(f);
            }
        }
        d1.shutdown();
    }
} /* Output: (85% match)
newSingleThreadExecutor().executor();
All asynch calls made
starting 0.0 + 0.0
starting 0.2 + 0.2
0.0
starting 0.4 + 0.4
0.4
starting 0.6 + 0.6
0.8
starting 0.8 + 0.8
1.2
starting 0 + 0
1.6
starting 1 + 1
0
starting 2 + 2
2
starting 3 + 3
4
starting 4 + 4
6
8
*///:~
