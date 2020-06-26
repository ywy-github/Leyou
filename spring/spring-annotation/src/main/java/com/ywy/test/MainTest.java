package com.ywy.test;

import com.ywy.config.MainConfig;
import com.ywy.pojo.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {
    public static void main(String[] args) {
//        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
//        //通过bean的id获取
//        //Person person = (Person) applicationContext.getBean("person");
//        //通过bean的类型获取
//        Person person = applicationContext.getBean(Person.class);
//        System.out.println(person);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        //通过bean的id获取
//        Person person = (Person) applicationContext.getBean("person");

        //通过bean的类型获取
        Person person = applicationContext.getBean(Person.class);

        System.out.println(person);

        String[] names = applicationContext.getBeanNamesForType(Person.class);
        for (String name : names) {
            System.out.println(name);
        }

    }
}
