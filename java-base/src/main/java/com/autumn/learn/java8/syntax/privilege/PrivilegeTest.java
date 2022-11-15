package com.autumn.learn.java8.syntax.privilege;

import com.autumn.learn.java8.syntax.privilege.packageA.ChildAEP;
import com.autumn.learn.java8.syntax.privilege.packageA.Parent;
import com.autumn.learn.java8.syntax.privilege.packageA.packageB.ChildBEP;
import com.autumn.learn.java8.syntax.privilege.packageC.ChildCEP;
import org.junit.Test;

public class PrivilegeTest {

    @Test
    public void test01() {
        Parent parent = new Parent();
        Parent parent1 = new ChildAEP();
        ChildAEP childAEP = new ChildAEP();
        ChildBEP childBEP  = new ChildCEP();

        System.out.println(parent instanceof ChildAEP); // false
        System.out.println(parent1 instanceof Parent); // true
        System.out.println(parent1 instanceof ChildAEP); // true
        System.out.println(childAEP instanceof Parent); // true
        System.out.println(childBEP instanceof  Parent); // true
    }

    @Test
    public void test02() {
        ChildAEP childAEP = new ChildAEP();
        System.out.println(childAEP.p1);
        // System.out.println(childAEP.p2);

    }

    @Test
    public void test03() {
        ChildBEP childBEP = new ChildBEP();
        System.out.println(childBEP.p1);
//         System.out.println(childBEP.p2);
    }

    @Test
    public void test04() {
        Parent parent = new Parent();
        System.out.println(parent.p1);
        // System.out.println(parent.p2);
    }


    @Test
    public void test05(){
        Parent parent = new ChildAEP();
        parent.f1();
//        parent.f2(); // 父类无法调用子类独有的方法
    }
}
