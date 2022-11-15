package com.autumn.learn.spring;

import com.autumn.learn.spring.aop.MyAspectWithJavaAnnotation;
import com.autumn.learn.spring.domain.Department;
import com.autumn.learn.spring.domain.Employee;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;

/**
 *
 * @author yl
 * @since 2022-11-14 23:09
 */
public class AnnotationConfigAppTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(AnnotationConfigAppTest.class);

    /**
     * AnnotationMetadata、MethodMetadata获取
     */
    @Test
    public void testBean_AnnotationMetadata() {
        ConfigurableApplicationContext context =
                new AnnotationConfigApplicationContext(AnnotationConfigAppTest.ConfigBeanAnnotationMetadata.class);
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
        AnnotatedBeanDefinition definition = (AnnotatedBeanDefinition) beanFactory.getBeanDefinition("myAspect");
        AnnotationMetadata annotationMetadata = null;
        MethodMetadata methodMetadata = null;

        annotationMetadata = definition.getMetadata();
        methodMetadata = definition.getFactoryMethodMetadata();

        System.out.println(annotationMetadata);
        System.out.println(methodMetadata);
    }

    @Configuration
    static class ConfigBeanAnnotationMetadata {

        @Bean("myAspect")
        public MyAspectWithJavaAnnotation myAspect() {
            return new MyAspectWithJavaAnnotation();
        }
    }


    /**
     * 验证 org.springframework.beans.BeanMetadataElement.getSource() 功能
     */
    @Test
    public void testFunction_getSource() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AnnotationConfigAppTest.ConfigFunctionGetSource.class);
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();

        // 情形一：手动注册一个BeanDefinition
        BeanDefinition beanDefinitionRegistry = new RootBeanDefinition();
        beanDefinitionRegistry.setBeanClassName(Department.class.getName());
        beanDefinitionRegistry.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        beanFactory.registerBeanDefinition("department", beanDefinitionRegistry);

        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("department");
        Object source = beanDefinition.getSource(); // null
        LOGGER.info("手动注册的BeanDefinition，source: {}", source);

        // 情形二：XML配置文件中生成的BeanDefinition
        beanDefinition = beanFactory.getBeanDefinition("myService");
        source = beanDefinition.getSource(); // null
        LOGGER.info("XML配置文件中生成的BeanDefinition，source: {}", source);

        // 情形三：从配置类生成的BeanDefinition
        beanDefinition = beanFactory.getBeanDefinition("employee");
        source = beanDefinition.getSource(); // org.springframework.core.type.StandardMethodMetadata@XXX
        LOGGER.info("配置类生成的BeanDefinition，source: {}", source);
    }

    @ImportResource({"classpath:aop/applicationContext-Aop.xml"})
    @Configuration
    static class ConfigFunctionGetSource {

        @Bean(name = "employee")
        public Employee createEmployee() {
            return new Employee();
        }
    }


    /**
     * 注解@Configuration的 proxyBeanMethods 属性功能
     * proxyBeanMethods为 true|false 时，注意对比department和employee.getDepartment()两个实例的hashcode
     */
    @Test
    public void testAnnotationProperty_proxyBeanMethods() {
        ConfigurableApplicationContext context =
                new AnnotationConfigApplicationContext(AnnotationConfigAppTest.ConfigPropertyProxyBeanMethods.class);

        LOGGER.info("** 测试@Configuration(proxyBeanMethods)属性");
        Department department = context.getBean("department", Department.class);
        LOGGER.info(String.valueOf(department.hashCode()));
        Employee employee = context.getBean("employee", Employee.class);
        LOGGER.info(String.valueOf(employee.getDepartment().hashCode()));
    }

    @Configuration(proxyBeanMethods = false)
    static class ConfigPropertyProxyBeanMethods {

        @Bean(name = "department")
        public Department createDepartment() {
            return new Department();
        }

        @Bean("employee")
        public Employee createEmployee() {
            // proxyBeanMethods = false, 每次调用createAA()方法，都返回一个新的AA实例；
            // proxyBeanMethods = true, 每次调用createAA()方法，都返回同一个已创建的AA实例
            Department department = createDepartment();
            return new Employee(department);
        }
    }


    /**
     * Bean生命周期测试
     */
    @Test
    public void testBeanLifeCycle() {
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AnnotationConfigAppTest.ConfigBeanLifeCycle.class);
        context.getBean("beanLifecycle"); // 多例bean的创建需通过调用getBean()触发
        context.close(); // 手动调用close()方法，触发bean生命周期中的destroy方法执行（多例模式Bean不会执行destroy方法）
    }

    @Configuration
    static class ConfigBeanLifeCycle {

        @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON) // 多例Bean的创建不会执行destroy方法; @Bean默认创建单例Bean
        @Bean(name = "beanLifecycle", initMethod = "customInit", destroyMethod = "customDestroy")
        public com.autumn.learn.spring.domain.BeanLifecycle beanLifecycle() {
            return new com.autumn.learn.spring.domain.BeanLifecycle();
        }
    }
}
