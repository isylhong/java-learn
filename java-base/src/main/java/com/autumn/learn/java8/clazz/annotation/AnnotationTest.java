package com.autumn.learn.java8.clazz.annotation;

import com.autumn.learn.java8.domain.phone.iPhone;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 注解测试
 *
 * @author yl
 * @since 2022-11-12 13:27
 */
public class AnnotationTest {
    private final Logger LOGGER = LoggerFactory.getLogger(AnnotationTest.class);

    @Test
    public void testMethod_isAnnotationPresent() {
        Class<iPhone> clazz = iPhone.class;
        System.out.println(clazz.isAnnotationPresent(CustomDescription.class));
    }

    /**
     * getAnnotatedInterfaces(): 获取Class对象对应类实现的所有带注解的接口类型
     * getAnnotatedSuperclass(): 返回父类的注解的AnnotatedType
     */
    @Test
    public void testMethod_getAnnotatedSuperclass() {
        Class<iPhone> clazz = iPhone.class;
        List<String> annotationNames = new ArrayList<>();

        // 获取实现的接口
        LOGGER.info("getAnnotatedInterfaces()获取实现的接口");
        AnnotatedType[] annotatedInterfaces = clazz.getAnnotatedInterfaces();
        for (AnnotatedType annotatedType : annotatedInterfaces) {
            String interfaceFullName = annotatedType.getType().getTypeName();
            annotationNames.add(interfaceFullName);
        }
        LOGGER.info("{}", annotationNames);

        // 获取父类的class name
        LOGGER.info("getAnnotatedSuperclass()获取父类的class name");
        AnnotatedType annotatedSuperclass = clazz.getAnnotatedSuperclass();
        String fullSuperclassName = annotatedSuperclass.getType().getTypeName();
        LOGGER.info("{}", fullSuperclassName);
    }

    /**
     * getDeclareAnnotation(Class<A> annotationClass)、
     * getDeclaredAnnotations()、
     * getDeclaredAnnotationsByType(Class<A> annotationClass)
     * 三个方法均只能获取标注类上的注解，无法获取标注类的父类及祖父类中的注解
     */
    @Test
    public void testMethod_getDeclareAnnotation() {
        Class<iPhone> clazz = iPhone.class;

        // Declared Annotation
        LOGGER.info("getDeclareAnnotation(xxx.class)");
        CustomDescription declaredAnnotation = clazz.getDeclaredAnnotation(CustomDescription.class);
        LOGGER.info("{}", declaredAnnotation);

        LOGGER.info("getDeclaredAnnotations()");
        Annotation[] declaredAnnotations = clazz.getDeclaredAnnotations();
        LOGGER.info("{}", Arrays.asList(declaredAnnotations));

        LOGGER.info("getDeclaredAnnotationsByType(xxx.class)");
        CustomDescription[] declaredAnnotationsByType =
                clazz.getDeclaredAnnotationsByType(CustomDescription.class);
        LOGGER.info("{}", Arrays.asList(declaredAnnotationsByType));
    }

    /**
     * getAnnotation(Class<A> annotationClass)、
     * getAnnotations()、
     * getAnnotationsByType(Class<A> annotationClass)
     * 三个方法均可获取标注类上的注解 + 父类及祖父类上可继承的注解(标注有@Inherited的注解)
     *
     * 注：
     * 1. 父类和子类中存在相同的注解时，子类注解会覆盖父类注解。
     * 2. getAnnotation(CustomAttribute.class)获取到的是CustomAttributes注解，CustomAttributes中含使用的CustomAttribute注解。
     * 3. 三个方法均不会获取任何标注在实现接口上的注解。
     */
    @Test
    public void testMethod_getAnnotation() {
        Class<iPhone> clazz = iPhone.class;

        // 获取标注类(祖父类中可继承的注解)的指定类型注解
        LOGGER.info("getAnnotation(XXX.class): 获取标注类上指定类型注解，及祖父类中可继承的注解");
        CustomDescription description = clazz.getAnnotation(CustomDescription.class);
        LOGGER.info("{}", description);

        // 获取标注类(含祖父类中可继承的注解)的所有注解
        LOGGER.info("getAnnotations(): 获取标注类的所有注解，及祖父类中可继承的注解");
        Annotation[] annotations = clazz.getAnnotations();
        LOGGER.info("{}", Arrays.toString(annotations));

        // 获取标注类(含祖父类中可继承的注解)的指定类型的所有注解
        LOGGER.info("获取标注类的指定注解类型的所有注解，及祖父类中可继承的注解");
        CustomDescription[] annotationsByType = clazz.getAnnotationsByType(CustomDescription.class);
        LOGGER.info("{}", Arrays.toString(annotationsByType));
    }
}
