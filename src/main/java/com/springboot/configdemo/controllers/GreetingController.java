package com.springboot.configdemo.controllers;

import com.springboot.configdemo.config.DbSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
public class GreetingController {

    @Value("${my.greeting}")
    private String greetingMessage;

    @Value("${app.description}")
    private String appDescription;

    @Value("${my.list.values}")
    private List<String> listValues;

    @Value("#{${db.values}}")
    private Map<String,String> dbValues;

    @Autowired
    private DbSettings dbSettings;

    @GetMapping("/greeting")
    public String greeting(){
        return greetingMessage + "\n" + appDescription + "\n" + listValues + "\n" + dbValues;
    }

    @GetMapping("/dbsettings")
    public String getDbDetails(){
        return dbSettings.getConnection() + ", " + dbSettings.getHost() + ", " + dbSettings.getPort();
    }
}
