package com.springboot.configdemo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GreetingController {

    @Value("${my.greeting : Default greeting message}")
    private String greetingMessage;

    @Value("${app.description}")
    private String appDescription;

    @Value("${my.list.values}")
    private List<String> listValues;

    @GetMapping("/greeting")
    public String greeting(){
        return greetingMessage + "\n" + appDescription + "\n" + listValues;
    }
}
