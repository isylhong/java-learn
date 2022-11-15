package com.autumn.learn.java8.syntax.AbstractInterface.interface_p;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 接口不能存在构造方法
 * 接口中的属性，访问修饰符默认为(public static final)，且必须在定义的时候赋初始值
 * 接口中的方法，访问修饰符默认为(public)
 * <p>
 * 接口中可以有已实现的default方法，default空方法；
 * 接口中的默认方法可以在实现类中重写
 * <p>
 * 接口可以有static方法，static空方法；
 * 接口中的static方法无法在实现类中重写
 */

public interface IParent {

    public static final Logger logger = LoggerFactory.getLogger(IParent.class);

    public static void main(String[] args) {
        IParent.f4();
        IParent.f5();
    }

    // 默认修饰符： public static final，
    int a = 0;

    void f1();


    // 默认方法
    default void f2() {
        logger.info("interface...default method...");
    }

    // 默认空方法
    default void f3() {
    }

    // static方法
    static void f4() {
        logger.info("interface... static method...");
    }

    // static空方法
    static void f5() {
    }

}
