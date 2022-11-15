package com.autumn.learn.java8.designpattern.wrapper;

/**
 * TODO
 *
 * @author yl
 * @since 2022-11-15 19:58
 */
public class BrushTeethBeforeSleep extends AbstractSleep {

    public BrushTeethBeforeSleep(Sleep sleep) {
        this.sleep = sleep;
    }

    @Override
    public void toSleep() {
        this.brushTeeth(); // 功能增强
        sleep.toSleep();
    }

    public void brushTeeth() {
        System.out.println("睡前刷牙。");
    }
}
