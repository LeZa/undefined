package com.build.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ServiceRegisterImpl
    implements  ServiceRegister ,Watcher{

    private static CountDownLatch latch = new CountDownLatch(1);

    private ZooKeeper zooKeeper;

    private static final int SESSION_TIMEOUT = 5000;

    private static final String REGISTER_PATH="/register";

    public ServiceRegisterImpl(){}

    public ServiceRegisterImpl( String zkServer ){

        try {
            zooKeeper = new ZooKeeper( zkServer,SESSION_TIMEOUT,this);
            latch.wait();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void register(String serviceName, String serviceAddress) {

        try {
            String registerPath = REGISTER_PATH;
            if (zooKeeper.exists(registerPath, false) != null) {}
            else{
                zooKeeper.create(registerPath,null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            String servicePath = registerPath + "/" +serviceName;
            if( zooKeeper.exists(servicePath,false) != null ){}
            else{
                zooKeeper.create(servicePath,null,ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }
            String addressPath = servicePath+"/address-";
            zooKeeper.create(addressPath,serviceAddress.getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT_SEQUENTIAL);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {

        if( event.getState() == Event.KeeperState.SyncConnected ){
            latch.countDown();
        }
    }
}
