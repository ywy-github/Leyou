package com.ywy.config;

import com.ywy.condition.LinuxCondition;
import com.ywy.condition.MyImportBeanDefinitionRegistrar;
import com.ywy.condition.MyImportSelector;
import com.ywy.condition.WindowsCondition;
import com.ywy.pojo.Color;
import com.ywy.pojo.ColorFactoryBean;
import com.ywy.pojo.Person;
import com.ywy.pojo.Red;
import org.springframework.context.annotation.*;
import sun.plugin2.os.windows.Windows;

@Configuration
//类中组件统一设置。满足当前条件，这个类中注册的所有bean才会生效
@Conditional({WindowsCondition.class})
//@Import(Color.class)
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
//@Import导入组件,id默认是全类名,可以接收一个数组，用来导入多个
public class MainConfig2 {
    /**
     * ConfigurableBeanFactory#SCOPE_PROTOTYPE
     * 	 ConfigurableBeanFactory#SCOPE_SINGLETON
     * 	 org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST
     * 	 org.springframework.web.context.WebApplicationContext#SCOPE_SESSION
     * @return
     *
     * prototype:多实例的：IOC容器启动并不会调用方法创建对象放入容器中，
     *                    获取时才会调用方法创建对象放入容器，并且以后每次使用都需要重新加载
     * singleton:单实例的(默认值):IOC容器启动就会调用方法创建对象放到IOC容器中，
     *         以后每次获取(相当于map.get())就是直接从容器中拿
     *
     * 懒加载：
     *          单实例bean：默认在容器启动的时侯创建对象
     *          懒加载：容器启动的时候不加载，第一次获取bean的时候才创建对象，并初始化，
     *                 而且以后再使用就不用重新加载，获取的是第一次使用时创建好的那个
     */
//    @Scope("prototype")
    @Lazy
    @Bean
    public Person person(){
        System.out.println("给容器中添加person------");
        return new Person("张三",25);
    }

    /**
     * @Conditional:按照一定的条件进行判断，满足条件才会给容器注册bean
     *
     * 如果系统是Windows，就给容器注册("bill")
     *
     * 如果系统是Linux，就给容器注册("linus")
     */

    @Bean("bill")
    public Person person1(){
        return new Person("Bill Gates",62);
    }

    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person2(){
        return new Person("Linus",48);
    }

    /**
     * 给容器中注册组件的方式:
     * 1)、包扫描+组件标注注解 (@Controller/@Service/@Repository/@Component) [局限于自己写的类]
     * 2)、@Bean [导入第三方包里面的组件]
     * 3)、@Import [快速给容器中导入一个组件]
     *        1）、@Import(要导入到容器中的组件);容器就会自动注册组件，id默认是全类名
     *        2）、ImportSelector:返回需要导入的组件的全类名数组
     *        3）、ImportBeanDefinitionRegistrar: 手动注册bean
     * 4）、使用spring提供的FactoryBean(工厂bean)
     *    1）、默认使用的是工厂bean调用getObject()创建的对象
     *    2）、要获取工厂bean本身，需要给id前加上一个&
     *        "&colorFactoryBean"
     */
    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }
}
