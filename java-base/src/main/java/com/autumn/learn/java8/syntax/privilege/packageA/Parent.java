package com.autumn.learn.java8.syntax.privilege.packageA;

public class Parent {

    public String p1 = "Parent...p1(public)...";
    protected String p2 = "Parent...p2(protected)...";
    String p3 = "Parent...p3(package)...";
    private String p4 = "Parent...p4(private)...";

    public void f1() {
        System.out.println("Parent...f1()...");
    }

}
