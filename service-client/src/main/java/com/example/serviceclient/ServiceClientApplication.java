package com.example.serviceclient;

import com.example.serviceclient.config.TokenInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
public class ServiceClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceClientApplication.class, args);
	}


	@Configuration
	static class TokenWebAdapter
			implements WebMvcConfigurer {

		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(new TokenInterceptor())
					.addPathPatterns("/**");
		}

	}

}
