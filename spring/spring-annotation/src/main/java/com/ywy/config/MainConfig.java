package com.ywy.config;

import com.ywy.pojo.Person;
import com.ywy.service.BookService;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

//配置类==配置文件
@Configuration

@ComponentScans(
        value = @ComponentScan(
                value = "com.ywy",
//        excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION,value = {Controller.class})
//}
                includeFilters = {
//                        @ComponentScan.Filter(type = FilterType.ANNOTATION,value= {Controller.class}),
//                        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {BookService.class}),
                        @ComponentScan.Filter(type = FilterType.CUSTOM,value = {MyTypeFilter.class})
                },
                useDefaultFilters = false
        )
)

//value:指定要扫描的包
//excludeFilters:Filter[]  指定扫描时要按什么规则排除组件
//includeFilters:Filter[]  指定扫描时只需要包含哪些组件  使用时需要禁用掉默认的扫描规则
//ComponentScan可以使用多次
//FilterType.ANNOTATION : 按照注解
//FilterType.ASSIGNABLE_TYPE : 指定类型
//FilterType.ASPECTJ : 按照ASPECTJ表达式
//FilterType.REGEX : 按照正则表达式
//FilterType.CUSTOM : 使用自定义的规则
public class MainConfig {
    //给容器注册一个bean，类型是返回值的类型，id 默认用方法名
    @Bean("person")
    public Person person01(){
        return new Person("lisi",20);
    }
}
