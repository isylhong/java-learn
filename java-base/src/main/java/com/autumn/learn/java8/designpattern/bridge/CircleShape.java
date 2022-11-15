package com.autumn.learn.java8.designpattern.bridge;

/**
 * 圆形
 *
 * @author yl
 * @since 2022-11-15 19:45
 */
public class CircleShape extends Shape {
    public CircleShape(Pen pen) {
        super(pen);
    }

    @Override
    public void drawing() {
        pen.color();
        System.out.println("画圆形");
    }
}
