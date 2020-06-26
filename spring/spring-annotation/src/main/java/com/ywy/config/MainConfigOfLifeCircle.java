package com.ywy.config;


import com.ywy.pojo.Car;
import com.ywy.pojo.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.io.CharArrayReader;

/**
 * bean的生命周期:
 *        bean的创建--->初始化--->销毁
 * 容器管理bean的生命周期
 * 我们可以自定义bean的创建和销毁方法；
 * 容器在bean进行到当前生命周期的时候来调用自定义的初始化和销毁方法
 *
 * 构造(创建对象)
 *     单实例:容器启动的时候创建对象
 *     多实例:每次使用的时候创建对象
 *
 * BeanPostProcessor.postProcessBeforeInitialization
 * 初始化:
 *     对象创建完成并赋值好，调用初始化方法
 * BeanPostProcessor.postProcessAfterInitialization
 *
 * 销毁:
 *     单实例:容器关闭的时候
 *     多实例:容器不会管理，容器不会调用销毁方法
 *
 * 遍历得到所有的BeanPostProcessor:挨个执行beforeInitialization
 * 一旦返回null，跳出for循环，不会执行后边的BeanPostProcessor.postProcessBeforeInitialization
 *
 *  BeanPostProcessor原理：
 *
 *  populateBean(beanName, mbd, instanceWrapper);给bean进行属性赋值
 *  initializeBean
 *  {
 *    applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName); 初始化前执行
 *    invokeInitMethods(beanName, wrappedBean, mbd);  初始化bean
 *    applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName); 初始化后执行
 *  }
 *
 * 1）、指定初始化和销毁方法
 *         init-method和destroy-method
 * 2）、通过让bean实现InitializingBean(定义初始化逻辑)
 *                  DisposableBean(定义销毁逻辑)
 * 3）、使用JDR250:
 *     @PostConstructor:在bean创建完成并且属性赋值完成，来执行初始化方法
 *     @PreDestroy:在容器销毁bean之前通知我们进行清理工作
 * 4）、BeanPostProcessor[interface]:bean的后置处理器;
 *         在bean初始化前后进行一些处理工作;
 *         postProcessBeforeInitialization:在初始化之前进行工作
 *         postProcessAfterInitialization:在初始化之后进行工作
 */
@Configuration
@ComponentScan("com.ywy.pojo")
public class MainConfigOfLifeCircle {


    @Scope("prototype")
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car(){
        return new Car();
    }

}
