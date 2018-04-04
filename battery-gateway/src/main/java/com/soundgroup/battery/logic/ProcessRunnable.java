package com.soundgroup.battery.logic;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.soundgroup.battery.event.CubeMsg;
import com.soundgroup.battery.event.EventEnum;
import com.soundgroup.battery.event.ReplyEvent;


public class ProcessRunnable implements Runnable {
	private static final Logger LOG = Logger.getLogger(ProcessRunnable.class);

    private ConcurrentHashMap<String, ReplyEvent> replyMap = new ConcurrentHashMap<String, ReplyEvent>();

    private ConcurrentLinkedQueue<CubeMsg> workqueue = new ConcurrentLinkedQueue<CubeMsg>();

    private volatile boolean isRunning = false;

    public void putReply(ReplyEvent reply) {
           replyMap.put(reply.getId(), reply);
    }

    public ReplyEvent getReply(String id) {
        return replyMap.get(id);
    }

    public ReplyEvent removeReply(String id) {
        return replyMap.remove(id);
    }
    
    public ConcurrentHashMap<String, ReplyEvent> getReplyAll() {
        return replyMap;
    }

    /**
     * Add queue  event.
     */
    public boolean pushUpMsg(CubeMsg msg) {
        if (isRunning) {
            synchronized (workqueue) {
                boolean ret = workqueue.add(msg);
                if(ret){
                    workqueue.notifyAll();
                }
                return ret;
            }
        } else {
            return false;
        }
    }
    
    public ProcessRunnable() {
        isRunning = false;
    }
 
    public void run() {
        isRunning = true;
        while (true) {
            try {
                CubeMsg msg = null;
                synchronized (workqueue) {
                    msg = workqueue.poll();
                    if(msg == null){
                        workqueue.wait();
                        continue;
                    }
                }

                EventEnum event = msg.getType();
                Process process = ProcessManager.getInstance().getProcess(event);
                process.excute(msg);
            } catch (Exception e) {
                LOG.error("ProcessRunnable Worker exception", e);
            }

        }
    }

}
