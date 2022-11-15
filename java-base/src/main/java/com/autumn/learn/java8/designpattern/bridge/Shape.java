package com.autumn.learn.java8.designpattern.bridge;

/**
 * 桥接类
 *
 * @author yl
 * @since 2022-11-15 19:44
 */
public abstract class Shape {
    protected Pen pen;

    public Shape(Pen pen) {
        this.pen = pen;
    }

    public abstract void drawing();
}
