package com.build.curatorframework;


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

    public static final String REGISTER_ROOT_PATH = "/mall";


    public static void main( String sck[] ) {
        try {
            CuratorFramework curatorFramework
                    = CuratorFrameworkFactory.newClient("192.168.89.54:2181",
                    new ExponentialBackoffRetry(1000, 3));
            curatorFramework.start();
            curatorFramework.blockUntilConnected();

            ServiceInstanceBuilder<ServiceDetail> serviceInstanceBuilder = ServiceInstance.builder();
            serviceInstanceBuilder.address("192.168.89.5");
            serviceInstanceBuilder.port(2185);
            serviceInstanceBuilder.name("tomcat");
            serviceInstanceBuilder.payload( new ServiceDetail("1",1) );

            ServiceInstance<ServiceDetail> instance = serviceInstanceBuilder.build();

            ServiceDiscovery<ServiceDetail> serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceDetail.class)
                    .client(curatorFramework)
                    .serializer(new JsonInstanceSerializer<ServiceDetail>(ServiceDetail.class))
                    .basePath(REGISTER_ROOT_PATH)
                    .build();

/*            ServiceInstanceBuilder<RocksdbService> serviceInstanceBuilder = ServiceInstance.builder();
            serviceInstanceBuilder.address("192.168.89.60");
            serviceInstanceBuilder.port(2185);
            serviceInstanceBuilder.name("sushile");
            RocksdbService rocksdbService =  new RocksdbServiceImpl();
            serviceInstanceBuilder.payload( rocksdbService );

            ServiceInstance<RocksdbService> instance = serviceInstanceBuilder.build();

            ServiceDiscovery<RocksdbService> serviceDiscovery = ServiceDiscoveryBuilder.builder(RocksdbService.class)
                    .client(curatorFramework)
                    .serializer(new JsonInstanceSerializer<RocksdbService>(RocksdbService.class))
                    .basePath(REGISTER_ROOT_PATH)
                    .build();*/
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
