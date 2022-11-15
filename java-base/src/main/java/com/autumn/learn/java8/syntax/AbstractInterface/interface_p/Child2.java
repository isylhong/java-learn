package com.autumn.learn.java8.syntax.AbstractInterface.interface_p;

public class Child2 implements IParent {


    @Override
    public void f1() {
        System.out.println("implement interface default method in Class Child2...");
    }

    @Override
    public void f3() {
        IParent.super.f3();
        System.out.println("override interface default method in Class Child2...");
    }
}
