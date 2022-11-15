package com.autumn.learn.java8.designpattern.bridge;

/**
 * 红笔
 *
 * @author yl
 * @since 2022-11-15 19:43
 */
public class RedPen implements Pen {
    @Override
    public void color() {
        System.out.println("红笔");
    }
}
