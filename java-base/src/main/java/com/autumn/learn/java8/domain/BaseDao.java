package com.autumn.learn.java8.domain;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author yl
 * @since 2022-11-12 13:19
 */
public class BaseDao<T> {
    private String parameterizedTypeClassName;

    public BaseDao() {
    }

    public void init() {
        Type type = this.getClass().getGenericSuperclass(); // getGenericSuperclass(), 可用于获取泛型的具体类型 (可把使用泛型想象为继承父类)
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] types = parameterizedType.getActualTypeArguments();
            Class<T> clazz = (Class<T>) types[0];
            parameterizedTypeClassName = clazz.getName();
        }
        System.out.println(this.getParameterizedTypeClassName());
    }

    public void setParameterizedTypeClassName(String parameterizedTypeClassName) {
        this.parameterizedTypeClassName = parameterizedTypeClassName;
    }

    public String getParameterizedTypeClassName() {
        return this.parameterizedTypeClassName;
    }
}
