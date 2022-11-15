package com.autumn.learn.java8.designpattern.bridge;

/**
 * 黑笔
 *
 * @author yl
 * @since 2022-11-15 19:42
 */
public class BlackPen implements Pen {
    @Override
    public void color() {
        System.out.println("黑笔");
    }
}
