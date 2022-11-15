package com.autumn.learn.java8.string;

import org.junit.Test;

public class StringTest {

    @Test
    public void test01(){
        String s1 = "a";
        String s2 = "b";
        String s3 = s1 + s2;
        String s4 = "a" + "b";
        String s5 ="ab";
        String s6 = new String("a" + "b");

        String s7 = s3.intern();
        String s8 = s5.intern();
        String s9 = s6.intern();


        System.out.println(s3.hashCode());
        System.out.println(s4.hashCode());
        System.out.println(s5.hashCode());
        System.out.println(s6.hashCode());
        System.out.println(s7.hashCode());
        System.out.println(s8.hashCode());
        System.out.println(s9.hashCode());
        System.out.println("----------------");
        System.out.println(System.identityHashCode(s3));
        System.out.println(System.identityHashCode(s4));
        System.out.println(System.identityHashCode(s5));
        System.out.println(System.identityHashCode(s6));
        System.out.println(System.identityHashCode(s7));
        System.out.println(System.identityHashCode(s8));
        System.out.println(System.identityHashCode(s9));
        System.out.println("----------------");


        System.out.println(s3==s4);
        System.out.println(s3==s5);
        System.out.println(s3==s6);
        System.out.println("------------");
        System.out.println(s4==s5);
        System.out.println(s4==s6);
        System.out.println("------------");
    }

}
