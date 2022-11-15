package com.autumn.learn.java8.syntax;

public class SuperThisTest {

    public void f(){
        System.out.println(this);
    }

    public static void main(String[] args) {
        SuperThisTest this1 = new SuperThisTest();
        SuperThisTest this2 = new SuperThisTest();
        System.out.println(this1);
        this1.f();
        System.out.println(this2);
        this2.f();
    }
}
