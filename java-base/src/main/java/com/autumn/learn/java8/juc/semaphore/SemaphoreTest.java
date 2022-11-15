package com.autumn.learn.java8.juc.semaphore;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author yl
 * @since 2022-11-12 14:30
 */
public class SemaphoreTest {

    /**
     * 信号量集的使用
     */
    @Test
    public void testSemaphoreSignal() {
        Semaphore semaphore = new Semaphore(2);
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 1000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 5; i++) {
            executorService.submit(new SemaphoreThread(semaphore, "thread" + i));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用 Semaphore 完成同步互斥顺序执行线程 A,B,C 线程任务
     */
    @Test
    public void testSemaphoreSync() {
        List<Semaphore> semaphoreList = new ArrayList<>();
        semaphoreList.add(new Semaphore(1));
        semaphoreList.add(new Semaphore(0));
        semaphoreList.add(new Semaphore(0));

        new SemaphoreTasks.ThreadFirst(semaphoreList).start();
        new SemaphoreTasks.ThreadSecond(semaphoreList).start();
        new SemaphoreTasks.ThreadThird(semaphoreList).start();

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
