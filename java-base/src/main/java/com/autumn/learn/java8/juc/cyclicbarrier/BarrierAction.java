package com.autumn.learn.java8.juc.cyclicbarrier;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author yl
 * @since 2022-11-12 14:17
 */
public class BarrierAction implements Runnable {
    private final AtomicInteger atomicInteger = new AtomicInteger(1);

    @Override
    public void run() {
        switch (atomicInteger.getAndIncrement()) {
            case 1:
                System.out.println("买菜完成！");
                break;
            case 2:
                System.out.println("洗菜完成！！");
                break;
            case 3:
                System.out.println("炒菜完成！！！");
                break;
            default:
                System.out.println("一起吃饭ing");
        }
    }
}
