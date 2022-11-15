package com.autumn.learn.java8.syntax.privilege.packageA;

public class ClassB {
    public static void main(String[] args) {
        Parent parent = new Parent();
        System.out.println(parent.p1); // 共有
        System.out.println(parent.p2); // 保护
        System.out.println(parent.p3); // package
//        System.out.println(parentA.p4); // 私有，无法访问
    }
}
