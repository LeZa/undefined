package com.soundgroup.battery.core.common;

/**
 * Created by fafu on 2017/7/10.
 */
public interface LifeCycle {
    void init() throws Exception;

    void startup() throws Exception;

    void shutdown() throws Exception;
}


