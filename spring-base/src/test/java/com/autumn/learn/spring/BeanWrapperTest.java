package com.autumn.learn.spring;

import org.junit.Test;
import org.springframework.beans.PropertyAccessorUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author yl
 * @since 2022-11-14 23:17
 */
public class BeanWrapperTest {

    @Test
    public void testPropertyValue() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath" +
                ":applicationContext2.xml"}, false);
        context.refresh(); // 手动刷新ApplicationContext容器

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("id_A");

        context.getBean("id_A");
    }

    @Test
    public void testGetNestedPropertySeparatorIndex() {
        String propertyPath = "scores[my.key].value";

        int firstPropertySeparatorIndex = getNestedPropertySeparatorIndex(propertyPath, false);
        System.out.println(firstPropertySeparatorIndex + " " + propertyPath.substring(0, firstPropertySeparatorIndex));

        int lastPropertySeparatorIndex = PropertyAccessorUtils.getLastNestedPropertySeparatorIndex(propertyPath);
        System.out.println(lastPropertySeparatorIndex + " " + propertyPath.substring(lastPropertySeparatorIndex + 1));
    }

    /**
     * 获取propertyPath(内嵌属性)中第一个(或最后一个)属性分割符(.)的index，不考虑数组和集合key中的分割符(.)
     * 如map[my.key].name:
     *  "my.key"为key
     *  "name"为map[my.key]的内嵌属性名, name属性分割符(.)的index为6
     */
    public int getNestedPropertySeparatorIndex(String propertyPath, boolean last) {
        boolean inKey = false;
        int length = propertyPath.length();
        int i = (last ? length - 1 : 0);
        while (last ? i >= 0 : i < length) {
            switch (propertyPath.charAt(i)) {
                case '[':
                case ']':
                    inKey = !inKey;
                    break;
                case '.':
                    if (!inKey) {
                        return i;
                    }
            }
            if (last) {
                i--;
            } else {
                i++;
            }
        }
        return -1;
    }
}
