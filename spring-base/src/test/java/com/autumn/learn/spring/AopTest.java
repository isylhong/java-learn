package com.autumn.learn.spring;

import com.autumn.learn.spring.aop.MyAspectWithJavaAnnotation;
import com.autumn.learn.spring.aop.MyService;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 切面通知(增强)执行顺序：
 * 环绕前置通知 -> 前置通知 -> method执行 -> 环绕后置通知 -> 后置通知 -> 后置返回
 *
 * @author yl
 * @since 2022-11-14 23:02
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:applicationContext-Aop.xml")
public class AopTest {

    @Test
    public void testAnnotationConfigAop() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(com.autumn.learn.spring.AopTest.ConfigAopAnnotation.class);
        MyService myService = context.getBean("myService", MyService.class);
        myService.save("注解配置开启AOP");
    }

    @EnableAspectJAutoProxy // 开启AOP功能
    @Configuration
    static class ConfigAopAnnotation {
        @Bean
        public MyService myService() {
            return new MyService();
        }

        @Bean
        public MyAspectWithJavaAnnotation myAspect() {
            return new MyAspectWithJavaAnnotation();
        }
    }


    @Test
    public void testXmlConfigAop() {
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{
                "aop/applicationContext-Aop.xml"}, false);
        applicationContext.refresh();

        MyService myService = applicationContext.getBean("myService", MyService.class);
        myService.save("XML配置文件开启AOP");
    }
}