package com.autumn.learn.java8.juc;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author yl
 * @since 2022-11-12 14:15
 */
public class CountDownLatchTest {

    /**
     * 多个线程执行各自任务，待所有线程都执行完任务后，执行最后的任务
     */
    @Test
    public void testCountDownLatch() {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0; i < 5; i++) {
            executorService.execute(new CountDownLatchThread(countDownLatch));
        }

        try {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    System.out.println("ceshi");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            executorService.execute(() -> System.out.println("test1"));
            executorService.shutdown(); // 线程池调用shutdown后便不再接收执行新提交的线程任务了
            executorService.execute(() -> System.out.println("test2")); // 线程池已关闭，不再执行这个人任务了
//            System.out.println(executorService.awaitTermination(10000L, TimeUnit.MILLISECONDS)); // 调用awaitTermination后会阻塞该线程，直到线程池中所有线程执行完
            countDownLatch.await();
            System.out.println("所有线程执行完后，执行最后任务...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class CountDownLatchThread implements Runnable {
        private final CountDownLatch latch;

        public CountDownLatchThread(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000L);
                System.out.println(Thread.currentThread().getName() + " 完成了任务");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
