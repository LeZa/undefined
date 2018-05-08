package com.build.curatorframework;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.Collection;

public class AppClient {

    private static final String REGISTER_ROOT_PATH="/mall";

    public static void main(String[] args) throws Exception{
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.89.54:2181",
                new ExponentialBackoffRetry(1000, 3));
        client.start();
        client.blockUntilConnected();

  /*      ServiceDiscovery<ServiceDetail> serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceDetail.class)
                .client(client)
                .basePath(REGISTER_ROOT_PATH)
                .build();
        serviceDiscovery.start();

        //根据名称获取服务
        Collection<ServiceInstance<ServiceDetail>> services = serviceDiscovery.queryForInstances("tomcat");
        for(ServiceInstance<ServiceDetail> service : services) {
           System.out.println( service.getPayload());
        }
*/

        ServiceDiscovery<RocksdbService> serviceDiscovery = ServiceDiscoveryBuilder.builder(RocksdbService.class)
                .client(client)
                .basePath(REGISTER_ROOT_PATH)
                .build();
        serviceDiscovery.start();

        //根据名称获取服务
        Collection<ServiceInstance<RocksdbService>> services = serviceDiscovery.queryForInstances("sushile");
        for(ServiceInstance<RocksdbService> service : services) {
            RocksdbService rocksdbService = service.getPayload();
                    rocksdbService.getVal("123a");
        }

        serviceDiscovery.close();
        client.close();
    }

}
