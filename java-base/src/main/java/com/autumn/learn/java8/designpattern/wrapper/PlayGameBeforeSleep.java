package com.autumn.learn.java8.designpattern.wrapper;

/**
 * TODO
 *
 * @author yl
 * @since 2022-11-15 19:58
 */
public class PlayGameBeforeSleep extends AbstractSleep {

    public PlayGameBeforeSleep(Sleep sleep) {
        this.sleep = sleep;
    }

    @Override
    public void toSleep() {
        this.playGame(); // 功能增强
        sleep.toSleep();
    }

    public void playGame() {
        System.out.println("睡前玩游戏。。。");
    }
}
