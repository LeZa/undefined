package com.eurekame.client.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@RestController
public class ClientController {

    @Value("${server.port}") String port;
    @RequestMapping("/hi")
    public String home(@RequestParam String name,@RequestParam String host) {
        return "hi "+name+",i am  a client; i am from port:" +port+" and my host is:"+host;
    }
}
