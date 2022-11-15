package com.autumn.learn.java8.syntax.constructor;

public class Child extends Parent {

    public Child() {
        // super()和this(parameter...)不能同时调用，因为两条语句都必须放在构造函数体的第一句
        super();
//        this("调用其他参构造函数");
        System.out.println("子类无参构造函数...");
    }

    public Child(String str) {
        System.out.println(str);
    }

    public Child(int a) {
        System.out.println(a);
    }

}
