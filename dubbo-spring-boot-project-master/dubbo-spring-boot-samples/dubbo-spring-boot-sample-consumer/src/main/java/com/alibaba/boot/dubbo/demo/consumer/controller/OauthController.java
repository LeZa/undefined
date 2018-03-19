package com.alibaba.boot.dubbo.demo.consumer.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class OauthController {

    @RequestMapping("/oauth/error")
    public String oauthError(){
        return "this is unauthorized error";
    }
}
