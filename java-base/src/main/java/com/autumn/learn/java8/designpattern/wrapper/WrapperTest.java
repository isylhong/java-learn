package com.autumn.learn.java8.designpattern.wrapper;

import org.junit.Test;

/**
 * 装饰器模式
 *
 * @author yl
 * @since 2022-11-15 19:56
 */
public class WrapperTest {
    @Test
    public void testWrapper() {
        Sleep sleep = new ToSleep();
        PlayGameBeforeSleep playGameBeforeSleep = new PlayGameBeforeSleep(sleep);
        BatheBeforeSleep batheBeforeSleep = new BatheBeforeSleep(playGameBeforeSleep);
        BrushTeethBeforeSleep brushTeethBeforeSleep = new BrushTeethBeforeSleep(batheBeforeSleep);
        brushTeethBeforeSleep.toSleep();
    }
}
