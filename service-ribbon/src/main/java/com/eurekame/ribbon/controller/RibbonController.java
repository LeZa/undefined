package com.eurekame.ribbon.controller;

import com.eurekame.ribbon.service.HelloService;
import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RibbonController {

    @Autowired
    HelloService helloService;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;



    @RequestMapping(value = "/hi")
    public String hi(@RequestParam String name){

        this.loadBalancerClient.choose("client");
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("client");

        System.out.println( ".....serviceId..............."+serviceInstance.getServiceId());
        System.out.println( ".....serviceHost............."+serviceInstance.getHost());
        System.out.println( ".....servicePort............."+serviceInstance.getPort());
        System.out.println( ".....serviceUri.............."+serviceInstance.getUri());


        String resultStr = restTemplate.getForObject("http://client/hi?name="+name+"&host="+serviceInstance.getHost(),String.class);
//        //Map<String,String> resultObj = instance1.getMetadata();


//        this.loadBalancerClient.choose("service-hi");
//         resultStr  = restTemplate.getForObject("http://service-hi/hi?name="+name,String.class);
//        System.out.println("222"+instance2.getHost()+":"+instance2.getServiceId()+":"+instance2.getPort());
        return resultStr;
    }

    @RequestMapping(value = "/hiHystrix")
    public String hiHystrix(@RequestParam String name){
        return helloService.hiService(name);
    }
}
