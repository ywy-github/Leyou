package com.ywy.pojo;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Bike implements InitializingBean, DisposableBean {

    public Bike(){
        System.out.println("bike constructor....");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("bike destroy.....");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("bike afterPropertiesSet.....");
    }
}
