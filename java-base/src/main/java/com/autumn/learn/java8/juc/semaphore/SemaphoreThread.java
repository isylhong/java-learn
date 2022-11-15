package com.autumn.learn.java8.juc.semaphore;

import java.util.concurrent.Semaphore;

/**
 *
 * @author yl
 * @since 2022-11-12 14:32
 */
public class SemaphoreThread extends Thread {

    private final Semaphore semaphore;

    public SemaphoreThread(Semaphore semaphore, String threadName) {
        super(threadName);
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " 获取一个信号量");
            Thread.sleep(2000L);
            System.out.println(Thread.currentThread().getName() + " done");
            semaphore.release();
            System.out.println(Thread.currentThread().getName() + " 释放一个信号量");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
