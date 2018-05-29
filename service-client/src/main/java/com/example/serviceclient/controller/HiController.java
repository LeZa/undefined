package com.example.serviceclient.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class HiController {

    @RequestMapping("/hi")
    @ResponseBody
    public String sayHi(){
        return "Hi there";
    }
}
