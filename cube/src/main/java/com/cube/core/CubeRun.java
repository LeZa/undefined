package com.cube.core;

import java.lang.management.ManagementFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import com.cube.conf.ApplicationConfig;


/**
 * 程序入口
 * @author niulu
 */
public class CubeRun {
	private static final Logger LOG = Logger.getLogger(CubeRun.class);
    
    public static final Object forWait = new Object();

    public static void main(String[] args) throws Exception {
        // 获取jvm名
        String vm = ManagementFactory.getRuntimeMXBean().getName();
        if (StringUtils.isBlank(vm)) {
            LOG.error("can't get pid");
            return;
        }

        LOG.info("Cube main starting at");
        AnnotationConfigApplicationContext context
                    = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        context.start();
        synchronized (forWait) {
        	forWait.wait();
		}
        context.close();

    }
    
}
