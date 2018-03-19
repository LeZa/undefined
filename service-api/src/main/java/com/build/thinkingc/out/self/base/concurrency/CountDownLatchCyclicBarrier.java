package com.build.thinkingc.out.self.base.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchCyclicBarrier {

	public static long time(Executor executor, int concurrency, final Runnable action) throws InterruptedException {

		final CountDownLatch ready = new CountDownLatch(concurrency);
		final CountDownLatch start = new CountDownLatch(1);
		final CountDownLatch done = new CountDownLatch(concurrency);

		for (int i = 0; i < concurrency; i++) {
			executor.execute(new Runnable() {
				public void run() {
					ready.countDown();
					try {
						start.await();
						action.run();
					} catch (InterruptedException iEx) {

					} finally {
						done.countDown();
					}
				}
			});
		}
		ready.await();
		long startNanos = System.nanoTime();
		start.countDown();
		done.await();
		return System.nanoTime() - startNanos;
	}

	public static void normalCountDownLatch() {

		CountDownLatch doneSignal = new CountDownLatch(2);
		Executor e = Executors.newCachedThreadPool();

		for (int i = 0; i < 2; ++i) // create and start threads
			e.execute(new WorkerRunnable(doneSignal, i));
		try {
			doneSignal.await(); // wait for all to finish
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public static void main(String sck[]) {
		
		
//		CountDownLatchDemo.main();
		
		/*
		 * ------------------------------CyclicBarrier-----------------------
		 */
		
		int nHorses = 7;
		int pause = 200;
		 new HorseRace( nHorses,pause);
		
//		try {
//			System.out.println(CountDownLatchCyclicBarrier.time(Executors.newScheduledThreadPool(2), 2, new Keym()));
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
}

class Keym implements Runnable {

	@Override
	public void run() {
		System.out.println("Keym.run()");
	}
}

class WorkerRunnable implements Runnable {
	private final CountDownLatch doneSignal;
	private final int i;

	WorkerRunnable(CountDownLatch doneSignal, int i) {
		this.doneSignal = doneSignal;
		this.i = i;
	}

	@Override
	public void run() {
		doWork(i);
		doneSignal.countDown();
	}
	void doWork(int i) {

	}
}

/*
 * ------------------------------------------From thinking in java  CountDownLatch--------------------------------------------------------------
 * 方法摘要
 * CountDownLatch.await() 
 * 使当前线程在锁存器倒计数至零之前一直等待，除非线程被中断。 
 */

class CountDownLatchDemo{
	static final int SIZE = 100;
	
	public  static void main(){
		ExecutorService exec = Executors.newCachedThreadPool();
		CountDownLatch latch =  new CountDownLatch(SIZE);
		/*
		 * WaitingTask 的 latch倒计数未至零之前一直等待
		 */
		for( int i = 0; i < 10; i++){
			exec.execute(new WaitingTask(latch));
		}
		/*
		 * TaskPortion则将latch倒计数减到零
		 */
		for( int i = 0;i < SIZE; i++){
			exec.execute(new TaskPortion(latch));
		}
	}
	
}

class TaskPortion 
		implements Runnable{
	
	private static int counter = 0;
	private final int id = counter++;
	private static Random random = new Random(47);
	private final CountDownLatch  latch;
	
	TaskPortion( CountDownLatch latch){
		this.latch = latch;
	}
	
	@Override
	public void run(){
		try {
			doWork();
			latch.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void doWork() throws InterruptedException{
		TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
		System.out.println(this+" complemeted.");
	}
	
	@Override
	public String toString(){
		return String.format("%1$-3d", id);
	}
}

class WaitingTask 
	implements Runnable{
	
	private static  int counter = 0;
	private final int id  = counter++;
	private final CountDownLatch latch;
	
	WaitingTask( CountDownLatch latch){
		this.latch = latch;
	}
	
	@Override
	public void run(){
		try {
			System.out.println(".........Not countdown to zero before...................");
			latch.await();
			System.out.println(".........Countdown to zero after.................");
			System.out.println(" Latch barrier passed for "+this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString(){
		return String.format("WaitingTask %1$-3d ", id);
	}
}
/*
 * 
 * ----------------------------From thinking in java CyclicBarrier------------------------------------------------------
 * CyclicBarrier.await() 在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待;
 * 跑马比赛,所有马匹准备好才进行
 */

class Horse 
	implements Runnable{
	
	private static int counter = 0 ;
	private final int id = counter++;
	private int strides = 0;
	private static Random random =  new Random(47);
	private static CyclicBarrier barrier;
	
	public Horse( CyclicBarrier barrier){
		Horse.barrier = barrier;
	}
	
	public synchronized  int getStrides(){
		return strides;
	}
	
	@Override
	public void run(){
			try {
				while( !Thread.interrupted() ){
					synchronized( this ){
						strides += random.nextInt(3);
					}
				barrier.await();
				}
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	
	public String toString(){
		return "Horse " +id+" ";                      
	}
	
	public String tracks(){
		StringBuilder sb =  new StringBuilder(1024);
		for( int i = 0; i < getStrides(); i++){
			sb.append("*");
		}
		sb.append(id);
		return sb.toString();
	}
}


class HorseRace {
	static final int FINISH_LINE = 75;
	private List<Horse> horses = new ArrayList<Horse>();
	private ExecutorService executors = Executors.newCachedThreadPool();
	private CyclicBarrier barrier;
	
	public HorseRace( int nHorses, final int pause){
		
		barrier = new  CyclicBarrier(nHorses,new Runnable(){
			
			@Override
			public void run(){
				
				StringBuilder sb =  new StringBuilder(1024);
				for( int i = 0; i < FINISH_LINE; i++){
					sb.append("=");
				}
				System.out.println(sb.toString());
				
				for(Horse horse : horses){
					if( horse.getStrides() >= FINISH_LINE ){
						System.out.println(horse + "won!");
						executors.shutdownNow();
						return;
					}
				}
				
				try {
					TimeUnit.MILLISECONDS.sleep(pause);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		for( int i = 0; i < nHorses; i++){
			Horse horse = new Horse(barrier);
			horses.add(horse);
			executors.execute(horse);
		}
	}
}







