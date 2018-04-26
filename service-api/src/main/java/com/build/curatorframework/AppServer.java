package com.build.curatorframework;

import com.build.db.RocksdbHolder;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceInstanceBuilder;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.concurrent.TimeUnit;

public class AppServer {


    public static void main( String sck[] ) {
        try {
            CuratorFramework curatorFramework
                    = CuratorFrameworkFactory.newClient("192.168.89.54:2181",
                    new ExponentialBackoffRetry(1000, 3));
            curatorFramework.start();
            curatorFramework.blockUntilConnected();
            ServiceInstanceBuilder<RocksdbHolder> serviceInstanceBuilder = ServiceInstance.builder();
            serviceInstanceBuilder.address("192.168.89.60");
            serviceInstanceBuilder.port(2185);
            serviceInstanceBuilder.name("sushile");
            RocksdbHolder rocksdbHolder = new RocksdbHolder();
            rocksdbHolder.create();
            serviceInstanceBuilder.payload(rocksdbHolder);

            ServiceInstance<RocksdbHolder> instance = serviceInstanceBuilder.build();

            ServiceDiscovery<RocksdbHolder> serviceDiscovery = ServiceDiscoveryBuilder.builder(RocksdbHolder.class)
                    .client(curatorFramework)
                    .serializer(new JsonInstanceSerializer<RocksdbHolder>(RocksdbHolder.class))
                    .basePath(RocksdbHolder.REGISTER_ROOT_PATH)
                    .build();
            //服务注册
            serviceDiscovery.registerService(instance);
            serviceDiscovery.start();

            TimeUnit.SECONDS.sleep(3600);

            serviceDiscovery.close();
            curatorFramework.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
