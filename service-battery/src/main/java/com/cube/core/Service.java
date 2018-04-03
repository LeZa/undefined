package com.cube.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

import com.cube.server.CubeBootstrap;
import com.cube.server.CubeBootstrapLong;
import com.cube.server.HttpBootstrap;
import com.cube.server.WebSocketBootstrap;

@Component
public class Service implements Lifecycle {

	private static final Logger LOG = Logger.getLogger(Service.class);

	private final static ExecutorService threadPool = Executors.newCachedThreadPool();
	private boolean started = false;
	private Future future;

	@Autowired
	private CubeBootstrap cubeBoostrap;

	@Autowired
	private CubeBootstrapLong cubeBootstrapLong;
	
	@Autowired
	private HttpBootstrap httpBootstrap;
	
	@Autowired
	private WebSocketBootstrap webSocketBootstrap;


	public void start() {
		LOG.info("Service serverRun start");
		started = true;
		future = threadPool.submit(cubeBoostrap);
		future = threadPool.submit(cubeBootstrapLong);
		future = threadPool.submit(webSocketBootstrap);

		int sleepCount = 5;
		while (!cubeBoostrap.isRun()) {
			if (sleepCount < 0) {
				future.cancel(true);
				return;
			}
			try {
				sleepCount--;
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOG.info("InterruptedException:", e);
				e.printStackTrace();
			}
		}
		//tcp 超长指令发送给手表
		int sleepCount2 = 5;
		while (!cubeBootstrapLong.isRun()) {
			if (sleepCount2 < 0) {
				future.cancel(true);
				return;
			}
			try {
				sleepCount2--;
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOG.info("InterruptedException:", e);
				e.printStackTrace();
			}
		}

		threadPool.submit(httpBootstrap);
	}


	public void stop() {
		LOG.info("Service serverRun stop");
		started = false;
		threadPool.shutdown();
		if (future != null) {
			future.cancel(true);
		}
	}

	public boolean isRunning() {
		LOG.info("Service serverRun isRunning");
		return started;
	}

	public boolean isInterface(Class clazz, Class infClazz) {
		if (clazz == Object.class) {
			return false;
		}
		Class[] ifaces = clazz.getInterfaces();
		boolean trueType = false;
		for (Class<?> iface : ifaces) {
			if (iface.equals(infClazz)) {
				trueType = true;
				break;
			}
		}
		if (trueType) {
			return true;
		}
		clazz = clazz.getSuperclass();
		return isInterface(clazz, infClazz);

	}

}
