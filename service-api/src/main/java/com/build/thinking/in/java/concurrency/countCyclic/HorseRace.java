package com.build.thinking.in.java.concurrency.countCyclic;

import java.util.concurrent.*;
import java.util.*;

import static com.build.thinking.in.java.net.mindview.util.Print.print;

class Horse implements Runnable {
  private static int counter = 0;

  private final int id = counter++;

  private int strides = 0;

  private static Random rand = new Random(47);

  private static CyclicBarrier barrier;

  public Horse(CyclicBarrier b) {
    barrier = b;
  }

  public synchronized int getStrides() {
    return strides;
  }

  public void run() {
    try {
      while(!Thread.interrupted()) {
        synchronized(this) {
          strides += rand.nextInt(3); // Produces 0, 1 or 2
        }
        barrier.await();
      }
    } catch(InterruptedException e) {
      e.printStackTrace();
    } catch(BrokenBarrierException e) {
      throw new RuntimeException(e);
    }
  }

  public String toString() {
    return "Horse " + id + " ";
  }

  public String tracks() {
    StringBuilder s = new StringBuilder();
    for(int i = 0; i < getStrides(); i++)
      s.append("*");
    s.append(id);
    return s.toString();
  }
}

public class HorseRace {

  static final int FINISH_LINE = 75;

  private List<Horse> horses = new ArrayList<Horse>();

  private ExecutorService exec =
            Executors.newCachedThreadPool();

  private CyclicBarrier barrier;

  public HorseRace(int nHorses, final int pause) {

    barrier = new CyclicBarrier(nHorses, new Runnable() {

      public void run() {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < FINISH_LINE; i++)
          s.append("="); // The fence on the racetrack
        print(s);
        for(Horse horse : horses)
          print(horse.tracks());
        for(Horse horse : horses)
          if(horse.getStrides() >= FINISH_LINE) {
            print(horse + "won!");
            exec.shutdownNow();
            return;
          }
        try {
          TimeUnit.MILLISECONDS.sleep(pause);
        } catch(InterruptedException e) {
          print("barrier-action sleep interrupted");
        }
      }

    });

    for(int i = 0; i < nHorses; i++) {
      Horse horse = new Horse(barrier);
      horses.add(horse);
      exec.execute(horse);
    }
  }

  /**
   * @Description  七匹马谁先超过75谁赢
   * @param args
   */
  public static void main(String[] args) {
    new HorseRace(7, 200);
  }
} /* (Execute to see output) *///:~
