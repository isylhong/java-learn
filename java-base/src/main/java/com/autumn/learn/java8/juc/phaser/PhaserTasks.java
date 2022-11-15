package com.autumn.learn.java8.juc.phaser;

import java.util.concurrent.Phaser;

/**
 *
 * @author yl
 * @since 2022-11-12 14:25
 */
public class PhaserTasks {

    static class XiaoMeiTask implements Runnable {
        private final Phaser phaser;

        public XiaoMeiTask(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000L);
                System.out.println(Thread.currentThread().getName() + " 去买肉.");
                Thread.sleep(1000L);
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " 开始切肉..");
                Thread.sleep(1000L);
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " 开始炒菜...");
                phaser.arriveAndAwaitAdvance();

                phaser.arriveAndDeregister();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class XiaoMingTask implements Runnable {
        private final Phaser phaser;

        public XiaoMingTask(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " 去买酱油.之后什么都不需要做，等待吃饭.");
                Thread.sleep(1000L);
                phaser.arriveAndAwaitAdvance();

                phaser.arriveAndDeregister();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class XiaoXueTask implements Runnable {
        private final Phaser phaser;

        public XiaoXueTask(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " 去买蔬菜.");
                Thread.sleep(1000L);
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + " 开始洗菜..");
                Thread.sleep(1000L);
                phaser.arriveAndAwaitAdvance();

                phaser.arriveAndDeregister();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
