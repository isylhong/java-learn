package com.autumn.learn.java8.syntax;


/**
 * 创建类实例，代码执行顺序
 * static成员变量 =》 static初始化代码块 =》 普遍成员变量 =》 普遍初始化代码块 =》 构造函数
 */

public class ObjectInitTest {
    private static int i;  // 默认初始化值 0
    private static char j;  // 默认初始化值 ''
    private int k;
    private boolean flag = true;

    public ObjectInitTest() {
        System.out.println("constructor...");
    }

    static {
        System.out.println("static code block....");
        System.out.println("i=" + i);
        System.out.println("j=" + j);
//        System.out.println("flag=" + flag); 报错，static初始化代码块无法访问non-static变量
    }

    {
        System.out.println("non-static code block...");
        System.out.println("k=" + this.k);
        System.out.println("flag=" + flag);
        print();
    }

    void print() {
        System.out.println("print()...");
        this.k += 99;
        System.out.println("k=" + (++this.k));
        System.out.println("i=" + i + ", j=" + j + ",flag=" + flag);
    }

    public static void main(String[] args) {
        new ObjectInitTest();
    }

}
