package com.ywy.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxCondition implements Condition {

    /**
     *
     * @param ConditionContext : 判断条件能使用的上下文（环境)
     * @param AnnotatedTypeMetadata : 注释信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //判断是否是Linux系统

        //1.获取到ioc使用的bean工厂
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        //2.获取类加载器
        ClassLoader classLoader = context.getClassLoader();

        //3.获取当前环境信息
        Environment environment = context.getEnvironment();

        //4.获取bean定义的注册类
        BeanDefinitionRegistry registry = context.getRegistry();
        //可以判断容器中bean的注入情况，也可以给容器注入bean
        boolean person = registry.containsBeanDefinition("person");

        String property = environment.getProperty("os.name");
        if(property.contains("Linux")){
           return true;
        }
        return false;
    }
}
