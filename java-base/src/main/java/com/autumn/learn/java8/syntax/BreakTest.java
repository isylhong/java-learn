package com.autumn.learn.java8.syntax;

public class BreakTest {
    public static void main(String[] args) {
        // 为for循环添加标签，以便可以在多层嵌套循环中使用break跳出指定循环体
        b:for (int i=0;i<2;i++){
            for (int j=0;j<3;j++){
                if (j==2){
                    break;
                }
                System.out.println(i+"-"+j);
                if (i==1){
                    break b;
                }
            }
        }
    }
}
