package com.ywy.condition;

import com.ywy.pojo.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     *
     * @param AnnotationMetadata :当前类的注解信息
     * @param BeanDefinitionRegistry :  BeanDefinition注册类
     *           把所有需要添加到容器中的类调用
     *           BeanDefinitionRegistry的registerBeanDefinition() 手动注册
     *
     */
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry){
        boolean b = registry.containsBeanDefinition("com.ywy.pojo.Red");
        boolean b1 = registry.containsBeanDefinition("com.ywy.pojo.Blue");
        if(b && b1){
            //指定bean的定义信息
            RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);

            //注册一个bean，并指定bean名
            registry.registerBeanDefinition("rainBow",beanDefinition);
        }
    }
}
