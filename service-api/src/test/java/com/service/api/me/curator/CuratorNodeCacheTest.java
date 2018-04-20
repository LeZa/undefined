package com.service.api.me.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.test.TestingServer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CuratorNodeCacheTest {

    String  path = null;
    NodeCache nodeCache = null;
    TestingServer testingServer = null;

    @Test
    public void nodeCacheTest(){
        try {
            testingServer =  new TestingServer();
            path="/francis/nodecache/a";
            String zookeeper = "192.168.89.54";
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeper, retryPolicy);
            client.start();
            path = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path,"init".getBytes());
            nodeCache = new NodeCache( client,path,false);
            nodeCache.start();

            nodeCache.getListenable().addListener(new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    if(nodeCache.getCurrentData()!=null){
                        byte[] bytes = client.getData().forPath(path);
                        Test123 test123 = (Test123) CommonUtil.ByteToObject(bytes);
                        System.out.println(test123.getName());
                    }

                }
            });
            Stat stat = new Stat();
           /* RocksDBHolder rocksDBHolder =  new RocksDBHolder();*/
           Test123 test123 = new Test123();
           test123.setName("123");
            client.setData().forPath(path,CommonUtil.ObjectToByte(test123));
            TimeUnit.MILLISECONDS.sleep(1000);
            client.delete().deletingChildrenIfNeeded().forPath(path);
            TimeUnit.MILLISECONDS.sleep( 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pathChildrenCache(){
        try {
            testingServer =  new TestingServer();
            path="/francis/nodecache/b";
            String zookeeper = "192.168.89.54";
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeper, retryPolicy);
            client.start();
            PathChildrenCache pathChildrenCache
                    =  new PathChildrenCache( client,path,true);
            pathChildrenCache.start( PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
            pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                    if( event.getType() == PathChildrenCacheEvent.Type.INITIALIZED){
                        System.out.println("create"+event.getData().getPath());
                    }else if( event.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED){
                        System.out.println("create"+event.getData().getPath());
                    }else if(event.getType()==PathChildrenCacheEvent.Type.CHILD_REMOVED){
                        System.out.println("remove:"+event.getData().getPath());
                    }else if(event.getType()==PathChildrenCacheEvent.Type.CHILD_UPDATED){
                        System.out.println("update:"+new String(event.getData().getData()));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
