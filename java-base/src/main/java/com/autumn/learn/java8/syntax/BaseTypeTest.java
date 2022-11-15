package com.autumn.learn.java8.syntax;

import org.junit.Test;

public class BaseTypeTest {

    // 基本类型与包装类型的定义
    @Test
    public void test01(){
        byte b1 = 1;
        short s1 = 2;
        int i1 = 4;
        long l1 = 8L;
        float f1 = 4.0F;
        double d1 = 8.0;
        boolean bl = true;
        char c1 = 'a';

        Byte B2 = new Byte(b1);
        Short S2 = new Short(s1);
        Integer I2 = new Integer(i1);
        Long L2 = new Long(l1);
        Float F2 = new Float(f1);
        Double D2 = new Double(d1);
        Boolean BL2 = new Boolean(bl);
        Character C2 = new Character(c1);
        String str = "abc";
    }

    // 基本类型与包装类型的转换
    @Test
    public void test02() {

        int i = 1;
        Integer I = 2;
        String s = "3";


        // int --> Integer
        I = new Integer(i);
        // int --> String
        s = i +"";


        // String --> int
        i = Integer.parseInt(s);
        // String --> Integer
        I = Integer.valueOf(s);


        // int --> String
        s = String.valueOf(i);
        // Integer --> String
        s = I.toString();

    }
}
