package com.autumn.learn.java8.syntax.privilege.packageA;

import com.autumn.learn.java8.syntax.privilege.packageA.packageB.ChildBEP;


public class ChildAEP extends Parent {

    @Override
    public void f1() {
        System.out.println("(@Override) ChildAEP...f1()...");
    }

    public void f2() {
        System.out.println("ChildAEP...f2()...");
    }

    public static void main(String[] args) {
        Parent parent = new Parent();
        System.out.println(parent.p1); // 共有
        System.out.println(parent.p2); // 保护
        System.out.println(parent.p3); // package
//        System.out.println(parentA.p4); // 私有，无法访问


        ChildAEP childAEP = new ChildAEP();
        System.out.println(childAEP.p1);
        System.out.println(childAEP.p2);

        ChildBEP childBEP = new ChildBEP();
//        System.out.println(childBEP.p1);
        System.out.println(childBEP.p2);
    }
}
