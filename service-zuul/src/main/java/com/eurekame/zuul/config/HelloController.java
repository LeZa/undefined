package com.eurekame.zuul.config;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping( value = "/hi")
    public String sayHi(){
        return "sayHi";
    }
}
