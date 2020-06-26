package com.ywy.test;

import com.ywy.config.MainConfigOfLifeCircle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTestLifeCircle {
    @Test
    public void test1(){

        //创建IOC容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCircle.class);

        System.out.println("容器创建完成...");

        //applicationContext.getBean("car");

        //关闭容器
        applicationContext.close();
        System.out.println("容器关闭....");
    }
}
