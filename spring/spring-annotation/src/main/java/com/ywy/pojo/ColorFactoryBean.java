package com.ywy.pojo;

import org.springframework.beans.factory.FactoryBean;

//创建一个Spring定义的工厂bean
public class ColorFactoryBean implements FactoryBean<Color> {

    //返回一个Color对象，这个对象会添加到容器
    @Override
    public Color getObject() throws Exception {
        System.out.println("ColorFactoryBean....创建bean");
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }


    //是否单例
    //true:单实例，在容器中只会保存一份
    //false：多实例，每次获取都会创建一个新的bean，调用getObject()
    @Override
    public boolean isSingleton() {
        return true;
    }
}
