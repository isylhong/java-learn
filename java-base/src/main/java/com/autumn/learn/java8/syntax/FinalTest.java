package com.autumn.learn.java8.syntax;

import com.autumn.learn.java8.entity.User;

/**
 * final关键字集中含义：
 * 1、修饰简单类型变量时，表示变量赋值后无法再修改
 * 2、修饰非简单类型(类实例对象)变量时，表示对象变量地址无法修改，但对象中的属性值可以修改
 * 3、修饰属性：属性只能在定义时，或构造函数中赋值
 * 4、修饰方法参数：参数值在方法体中不能修改
 * 5、定义在方法体中，内部类中需要访问所在方法中的局部变量var时，var应使用final修饰
 * 5、定义在方法体中的内部类需要访问定义在内部类外方法中的局部变量var时，变量var应使用final修饰
 */

public class FinalTest {
    private final int num1 = 1;
    private final int num2;
    private final User user1 = new User();
    private final User user2;

    public FinalTest(int num, User user) {
        this.num2 = num;
        this.user2 = user;
    }

    public void print1() {
//        this.num=1; // 报错
//        this.user1 = new User(); // 报错
        User user3 = new User();
        this.user1.setName("zhangsan");
        this.user1.setAge(1);
        System.out.println(this.user1);
        this.user2.setAge(11);
    }

    private void print2() {
        user2.setAge(2);
        System.out.println(this.user2);
    }

    public static void main(String[] args) {
        User user = new User("lisi", 99);
        FinalTest finalTest = new FinalTest(3, user);
        finalTest.print1();
        finalTest.print2();
    }

}
