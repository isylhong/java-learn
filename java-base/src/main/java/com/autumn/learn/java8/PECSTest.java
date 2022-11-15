package com.autumn.learn.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yl
 * @since 2022-11-12 13:54
 */
public class PECSTest {

    /**
     * <? extends XXX> 限制添加元素，只可以取元素
     */
    @Test
    public void testExtends() {
        System.out.println("<? extends T> 测试：");
        List<B> baseList = new ArrayList<>();
        baseList.add(new B());
        baseList.add(new C());

        List<? extends B> list = baseList;
//        list.add(new A()); // 无法通过编译，限制添加
//        list.add(new B()); // 无法通过编译，限制添加
//        list.add(new C()); // 无法通过编译，限制添加

        B b = list.get(0); // 正常取元素
        B b1 = list.get(1); // 正常取元素
        System.out.println(b);
        System.out.println(b1);
    }

    /**
     * <? super XXX> 可添加XXX及其子类对象，但获取元素无法赋值给除Object以外的其他任何类型
     */
    @Test
    public void testSuper() {
        System.out.println("<? super T> 测试：");
        List<? super B> list = new ArrayList<>();
//        list.add(new A()); // 无法通过编译，限制添加B的祖父类对象
        list.add(new B()); // 正常添加B
        list.add(new C()); // 正常添加B子类

        Object object = list.get(0); // 取出的值只能赋值给Object类型变量
        Object object1 = list.get(1); // 取出的值只能赋值给Object类型变量
//        B b = list.get(0); // 无法将取出的值赋值给B类型
//        C c = list.get(0); // 无法将取出的值赋值给B的子类类型
        System.out.println(object);
        System.out.println(object1);
    }

    public interface I {
        String show();
    }

    static class A implements I {
        @Override
        public String show() {
            return "A.";
        }
    }

    static class B extends A {
        @Override
        public String show() {
            return "B.";
        }
    }

    static class C extends B {
        @Override
        public String show() {
            return "C.";
        }
    }
}
