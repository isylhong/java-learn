package com.autumn.learn.java8.juc.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *
 * @author yl
 * @since 2022-11-12 14:18
 */
public class CyclicBarrierThread implements Runnable {

    private final CyclicBarrier cyclicBarrier;

    public CyclicBarrierThread(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " 去买菜.");
            Thread.sleep(1000L);
            cyclicBarrier.await();
            System.out.println(Thread.currentThread().getName() + " 开始洗菜..");
            Thread.sleep(1000L);
            cyclicBarrier.await();
            System.out.println(Thread.currentThread().getName() + " 开始炒菜...");
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
