package com.autumn.learn.java8.clazz.annotation;

import com.autumn.learn.java8.domain.phone.iPhone;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yl
 * @since 2022-11-12 13:29
 */
@Inherited
@Target(value = {ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomAttributes {
    CustomAttribute[] value();
}
