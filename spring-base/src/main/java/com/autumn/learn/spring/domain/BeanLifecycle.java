package com.autumn.learn.spring.domain;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 初始化执行顺序：构造函数 -> @PostConstruct注解标注的初始化方法 -> InitializingBean.afterPropertiesSet() -> xml配置(init-method)指定的自定义方法
 * 销毁执行顺序：@PreDestroy注解标注的销毁方法 -> DisposableBean.destroy() -> xml配置(destroy-method)指定的自定义方法
 *
 * @author yl
 * @since 2022-11-14 23:05
 */
public class BeanLifecycle implements InitializingBean, DisposableBean {
    private static int createCount = 0;

    public BeanLifecycle() {
        System.out.println("1 execute constructor, 第 " + (++createCount) + " 次创建 BeanLifecycle 实例对象");
    }

    @PostConstruct
    public void initByPostConstructor() {
        System.out.println("2.1 init By @PostConstruct.");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("2.2 init by InitializingBean.afterPropertiesSet().");
    }

    public void customInit() {
        System.out.println("2.3 invoke custom init method.");
    }

    @Override
    public void destroy() {
        System.out.println("3.2 destroy by DisposableBean.destroy().");
    }

    @PreDestroy
    public void destroyByPreDestroy() {
        System.out.println("3.1 destroy by @PreDestroy.");
    }

    public void customDestroy() {
        System.out.println("3.3 invoke custom destroy method.");
    }

    @Override
    public String toString() {
        return "User@" + Integer.toHexString(hashCode());
    }
}
