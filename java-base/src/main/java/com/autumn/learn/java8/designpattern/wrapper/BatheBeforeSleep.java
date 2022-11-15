package com.autumn.learn.java8.designpattern.wrapper;

/**
 * TODO
 *
 * @author yl
 * @since 2022-11-15 19:57
 */
public class BatheBeforeSleep extends AbstractSleep {

    public BatheBeforeSleep(Sleep sleep) {
        this.sleep = sleep;
    }

    @Override
    public void toSleep() {
        this.bathe(); // 功能增强
        sleep.toSleep();
    }

    public void bathe() {
        System.out.println("睡前洗澡。。");
    }
}