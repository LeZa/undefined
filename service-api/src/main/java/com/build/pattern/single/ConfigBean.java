package com.build.pattern.single;


import com.build.pattern.single.ExtendsSingleton;
import com.build.pattern.single.SingletonBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBean {

    @Bean
    public SingletonBean getSingletonBean(){
        return new ExtendsSingleton(" extendsSingleton");
    }

    @Bean
    public A getA(){
        return  new ExtendsA(" extendsA");
    }
}
