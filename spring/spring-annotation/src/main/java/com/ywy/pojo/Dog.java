package com.ywy.pojo;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog {
    public Dog(){
        System.out.println("Dog construct...");
    }

    @PostConstruct
    public void init(){
        System.out.println("Dog init...");
    }
    @PreDestroy
    public void destroy(){
        System.out.println("Dog destroy...");
    }
}
