package com.build.api.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static com.build.thinking.in.java.net.mindview.util.Print.print;
import static com.build.thinking.in.java.net.mindview.util.Print.printnb;
import static java.util.concurrent.TimeUnit.*;

public class BlockQueueDelayQueue {

}




class DelayedTask
	implements Runnable,Delayed{
	
	private static int counter = 0;
	private  final int id = counter++;
	private  final int delta;
	private  final long trigger;
	protected static List< DelayedTask>   sequence = 
			new ArrayList<DelayedTask>();
	
	
	public DelayedTask( int delayInMilliseconds ){
		delta = delayInMilliseconds;
		trigger = System.nanoTime() +
					NANOSECONDS.convert(delta,MILLISECONDS);
	}
	
	@Override
	public void run(){
		printnb( this + " ");
	}
	
	@Override
	public long getDelay( TimeUnit timeUnit){
		return timeUnit.convert(trigger - System.nanoTime(),NANOSECONDS);
	}

	@Override
	public int compareTo(Delayed arg0) {
		DelayedTask that = (DelayedTask) arg0;
		if( trigger < that.trigger ) return -1;
		if( trigger > that.trigger )  return 1;
		return 0;
	}

	@Override
	public String toString(){
		return String.format("[%1$-4d]", delta)+" Task"+id;
	}
	
	public String summary(){
		return "(" + id + ":"+delta+")";
	}
	
	public static class EndSentinel extends DelayedTask{

		private  ExecutorService exec;
		
		public EndSentinel(int delayInMilliseconds,ExecutorService exec) {
			super(delayInMilliseconds);
			exec = exec;
		}
		
		public void run(){
			for( DelayedTask pt : sequence){
				printnb( pt.summary() + " ");
			}
			print();
			print( this + " Calling shutdownNow()");
			exec.shutdownNow();
		}
	}
}


class DelayedTaskConsumer 
	implements Runnable{
	
	private DelayQueue<DelayedTask> q;
	
	public void run(){
		
	}
}
