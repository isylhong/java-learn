package com.autumn.learn.java8.clazz.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

/**
 * 注解继承测试
 *
 * @author yl
 * @since 2022-11-14 22:53
 */
public class AnnotationInheritedTest {

    @Inherited // 声明注解具有继承性
    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    @interface AInherited {
        String value() default "";
    }

    @Inherited // 声明注解具有继承性
    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    @interface BInherited {
        String value() default "";
    }

    // 未声明注解具有继承性
    @Target(value = ElementType.TYPE)
    @Retention(value = RetentionPolicy.RUNTIME)
    @interface CInherited {
        String value() default "";
    }

    @AInherited("父类的AInherited")
    @BInherited("父类的BInherited")
    @CInherited("父类的CInherited")
    class SuperClass {
    }

    @BInherited("子类的BInherited")
    class ChildClass extends SuperClass {
    }

    public static void main(String[] args) {
        Annotation[] annotations = ChildClass.class.getAnnotations();
        System.out.println(Arrays.toString(annotations));
        // [@com.autumn.java8.annotation.AnnotationInheritedTest$AInherited(value=父类的AInherited),
        //  @com.autumn.java8.annotation.AnnotationInheritedTest$BInherited(value=子类的BInherited)]
    }
}
