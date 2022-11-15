package com.autumn.learn.java8.designpattern.bridge;

import org.junit.Test;

/**
 * 桥接模式
 *
 * 优点:
 * 你可以创建与平台无关的类和程序。
 * 客户端代码仅与高层抽象部分进行互动， 不会接触到平台的详细信息。
 * 开闭原则。 你可以新增抽象部分和实现部分， 且它们之间不会相互影响。
 * 单一职责原则。 抽象部分专注于处理高层逻辑， 实现部分处理平台细节。
 *
 * 缺点:
 * 对高内聚的类使用该模式可能会让代码更加复杂。
 *
 * 应用场景:
 * 拆分或重组一个具有多重功能的庞杂类 （例如能与多个数据库服务器进行交互的类），可以使用桥接模式。
 * 如果你希望在几个独立维度上扩展一个类，可使用该模式。
 * 如果你需要在运行时切换不同实现方法，可使用桥接模式。
 * @author yl
 * @since 2022-11-15 19:41
 */
public class BridgeTest {
    @Test
    public void testBridge(){
        Pen redPen = new RedPen(); // 红笔
        Pen blackPen = new BlackPen(); // 黑笔

        Shape circleShape = new CircleShape(redPen);
        circleShape.drawing(); // 红笔画圆形

        System.out.println();
        Shape circleShape2 = new CircleShape(blackPen);
        circleShape2.drawing(); // 黑笔画圆形

        System.out.println();
        Shape rectangularShape = new RectangularShape(redPen);
        rectangularShape.drawing(); // 红笔画矩形

        System.out.println();
        Shape rectangularShape2 = new RectangularShape(blackPen);
        rectangularShape2.drawing(); // 黑笔画矩形
    }
}
