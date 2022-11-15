package com.autumn.learn.spring.domain;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;

/**
 *
 * @author yl
 * @since 2022-11-14 23:08
 */
public class IgnoreAutowiringBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 在Application指明Bean属性可以通过类型自动注入时, 忽略ArrayList类型属性的自动注入
        beanFactory.ignoreDependencyType(ArrayList.class);
    }
}
