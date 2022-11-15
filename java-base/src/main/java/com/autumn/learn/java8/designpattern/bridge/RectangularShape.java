package com.autumn.learn.java8.designpattern.bridge;

/**
 * 矩形
 *
 * @author yl
 * @since 2022-11-15 19:45
 */
public class RectangularShape extends Shape {

    public RectangularShape(Pen pen) {
        super(pen);
    }

    @Override
    public void drawing() {
        pen.color();
        System.out.println("画矩形");
    }
}