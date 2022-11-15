package com.autumn.learn.java8.syntax;


public class TryCatchFinallyTest {
    public int print(){
        int num =6;

//        return num; // 直接返回，不执行finally语句

        try {

//            System.exit(0); // finally语句不会被执行

            int a = num/0;

            return 1; // finally语句会被执行

        }catch (Exception e){
            System.out.println("catch 中语句被执行");
//            return 2; // finally语句会被执行
        }finally {
            System.out.println("finally 中语句体被执行");
            throw new NullPointerException("空指针异常");
        }

//        return 4;
    }

    public static void main(String[] args) {
        int a = 1;
        System.out.println(new TryCatchFinallyTest().print());
    }

}
