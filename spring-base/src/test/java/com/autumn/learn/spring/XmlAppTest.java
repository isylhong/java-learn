package com.autumn.learn.spring;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author yl
 * @since 2022-11-14 23:10
 */
public class XmlAppTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(XmlAppTest.class);

    /**
     * beanFactory.ignoreDependencyType((Class<?> type)
     * 在Application指明Bean属性可以通过类型自动注入时, 忽略type类型属性的自动注入
     *
     * 注意：
     *   1、要让ignoreDependencyType()方法功能生效，需在配置文件中的beans标签设置属性default-autowire="byType"
     */
    @Test
    public void testMethod_IgnoreDependencyType() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath" +
                ":applicationContext3.xml"}, false);

        // 注意：在这里手动加入ApplicationContext中，无法起作用
//        context.addBeanFactoryPostProcessor(new IgnoreAutowiringBeanFactoryPostProcessor());

        context.refresh(); // 手动刷新ApplicationContext容器

//        Department department = context.getBean("department", Department.class);
//        LOGGER.info(department.toString());
    }


    /**
     * 循环依赖测试
     */
    @Test
    public void testBeanCircularReference() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath" +
                ":bean/applicationContext-CircularReference.xml"}, false);
        context.refresh(); // 手动刷新ApplicationContext容器
    }

    /**
     * 验证三个依赖Map分别存储的内容:
     * 1. Map<String, Set<String>> containedBeanMap = new ConcurrentHashMap<>(16);
     * 2. Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64); 存储被依赖的BeanName(类外部Bean)
     * 3. Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64); 存储类内部依赖的BeanName(类内部属性Bean)
     */
    @Test
    public void testBeanDependentMap() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath" +
                ":bean/applicationContext-CircularReference.xml"});

        // 先获取到Context中的BeanFactory，因context.close()调用后会置BeanFactory为null
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();

        String[] aDependencies = beanFactory.getDependenciesForBean("id_A"); // ["id_B"]
        String[] aDependents = beanFactory.getDependentBeans("id_A"); // ["id_B", "id_D"]

        String[] bDependencies = beanFactory.getDependenciesForBean("id_B"); // ["id_A", "id_C"]
        String[] bDependents = beanFactory.getDependentBeans("id_B"); // ["id_A"]

        String[] cDependencies = beanFactory.getDependenciesForBean("id_C"); // []
        String[] cDependents = beanFactory.getDependentBeans("id_C"); // ["id_B", "id_D"]

        System.out.println();
    }

    /**
     * Bean生命周期:
     * 第一步：实例化对象
     *   调用构造函数实例化一个Bean。
     * 第二步：为Bean的属性赋值
     *   调用populateBean(...)为相关属性赋值 -> 调用Bean实现的各种XXXAware接口的setXXX()方法为相关属性赋值
     * 第三步：调用BeanPostProcessor.postProcessBeforeInitialization()
     * 第四步：执行Bean的初始化方法
     *   调用@PostConstruct标注的初始化方法 -> 调用InitializingBean.afterPropertiesSet() -> 调用自定义指定的初始化方法
     * 第五步: 调用BeanPostProcessor.postProcessAfterInitialization()
     * ...
     * Bean一系列的方法调用，直到这个Bean不再需要使用
     * ...
     * 第六步：执行Bean的销毁方法
     *   调用@PostConstruct标注的销毁方法 -> 调用DisposableBean.destroy() -> 调用自定义指定的销毁方法
     *
     */
    @Test
    public void testBeanLifeCycle() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:" +
                "bean/applicationContext-BeanLifecycle.xml");

//        context.getBean("beanLifecycle"); // 多例bean的创建需通过调用getBean()触发

        context.close(); // 手动调用close()方法，触发bean生命周期中的destroy方法执行（多例模式Bean不会执行destroy方法）
    }
}
