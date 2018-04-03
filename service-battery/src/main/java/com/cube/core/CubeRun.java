package com.cube.core;

import java.lang.management.ManagementFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cube.conf.ApplicationConfig;

/**
 * @Description  main class
 */
public class CubeRun {

	private static final Logger LOG = Logger.getLogger(CubeRun.class);
    public static final Object forWait = new Object();

    private static AnnotationConfigApplicationContext context;

    public static void main(String[] args) throws Exception {

        String vm = ManagementFactory.getRuntimeMXBean().getName();
        if (StringUtils.isBlank(vm)) {
            LOG.error("can't get pid");
            return;
        }

        /**
         * @Description Start spring annotation
         */
        context   = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        context.start();
        synchronized (forWait) {
        	forWait.wait();
		}
        context.close();

    }

    public static  AnnotationConfigApplicationContext getApplicationContext(){
        return context;
    }
    
}
