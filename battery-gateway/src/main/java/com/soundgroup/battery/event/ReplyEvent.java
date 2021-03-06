package com.soundgroup.battery.event;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReplyEvent {

    private static final AtomicInteger ATOMIC_ID = new AtomicInteger(1);

    private Lock lock = new ReentrantLock();
    /**
     * 应答id
     */
    private final String id;
    /**
     * 应答信息
     */
    private volatile Object obj;

    public ReplyEvent(String SN) {
        this.id = SN;
    }

    public Object getObj() {
        try {
            lock.lock();
            return obj;
        } finally {
            lock.unlock();
        }
    }

    public void setObj(Object obj) {
        try {
            lock.lock();
            this.obj = obj;
        } finally {
            lock.unlock();
        }
    }

    public String getId() {
        return id;
    }
}
