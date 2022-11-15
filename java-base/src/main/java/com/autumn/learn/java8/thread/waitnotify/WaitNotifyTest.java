package com.autumn.learn.java8.thread.waitnotify;

import org.junit.Test;

/**
 * 使用wait()、notify()、notifyAll()模拟CountDownLatch
 *
 * @author yl
 * @since 2022-11-12 14:51
 */
public class WaitNotifyTest {

    @Test
    public void testWaitAndNotify() {
        Monitor monitor = new Monitor(2);
        Object obj = new Object();

        Thread waiterA = new Thread(new Waiter(monitor, obj), "<waiterA>");
        waiterA.start();

        Thread waiterB = new Thread(new Waiter(monitor, obj), "<waiterB>");
        waiterB.start();

        Thread notifier = new Thread(new Notifier(monitor, obj), "<notifier>");
        notifier.start();

        while (!monitor.isFinish()) ;

//        try {
//            Thread.sleep(10000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
