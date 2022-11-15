package com.autumn.learn.java8.juc.phaser;

import java.util.concurrent.Phaser;

/**
 *
 * @author yl
 * @since 2022-11-12 14:24
 */
public class MyPhaser extends Phaser {
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case 0:
                System.out.println("买食材: " + registeredParties + "人");
                return false;
            case 1:
                System.out.println("处理食材: " + registeredParties + "人");
                return false;
            case 2:
                System.out.println("炒菜: " + registeredParties + "人");
//                this.forceTermination();
                return false;
            default:
                return true;
        }
    }
}
