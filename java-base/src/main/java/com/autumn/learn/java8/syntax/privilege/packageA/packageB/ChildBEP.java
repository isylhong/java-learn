package com.autumn.learn.java8.syntax.privilege.packageA.packageB;


import com.autumn.learn.java8.syntax.privilege.packageA.Parent;

public class ChildBEP extends Parent {

    public String p1 ="(@Override) ChildBEP...p1(public)...";

    public void f2(){
        f1(); // 调用父类方法f1() 等效于 this.f()
    }

    public static void main(String[] args) {
        Parent parent = new Parent();
        System.out.println(parent.p1); // 共有
//        System.out.println(parent.p2); // 保护
//        System.out.println(parent.p3); // package
//        System.out.println(parentA.p4); // 私有，无法访问


        ChildBEP childBEP = new ChildBEP();
        System.out.println(childBEP.p1);
        System.out.println(childBEP.p2);
        childBEP.f2();

    }

}
