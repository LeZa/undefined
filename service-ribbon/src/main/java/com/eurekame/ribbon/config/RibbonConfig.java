package com.eurekame.ribbon.config;

import com.eurekame.ribbon.config.rule.BestRule;
import com.eurekame.ribbon.config.rule.RandRule;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RibbonConfig {


    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }


    /**
     * 1 RandomRule:三个client,如果有一个client断掉  RandomRule会一直在三个client中随机获取server
     * 2 BestAvailableRule:三个client,如果有一个client断掉  BestAvailableRule会有概率性的出现请求失败(100次调用大约出现5次以内)
     * 3 ZoneAvoidanceRule:
     */

//     @Bean
//    public IRule ribbonRule(){
//        return new ZoneAvoidanceRule();
//    }


}
