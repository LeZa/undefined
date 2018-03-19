package com.eurekame.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableAutoConfiguration
@RibbonClient(name="client")
public class ServiceClientribbonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceClientribbonApplication.class, args);
	}
}
