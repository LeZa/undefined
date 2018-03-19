package com.build.thinkingc.out.self.base.concurrency;

import java.util.concurrent.TimeUnit;

public class StopThread {

	private static boolean stopRequested;
	
	public static void main( String[] sck ) throws InterruptedException{
		
		Thread backgroundThread =  new Thread( new Runnable() {
			public void run(){
				int n = 0;
				while( !stopRequested )
					n++;
			}
		});
		
		stopRequested = true;
		backgroundThread.start();
		TimeUnit.SECONDS.sleep(1);
		
	}
}
