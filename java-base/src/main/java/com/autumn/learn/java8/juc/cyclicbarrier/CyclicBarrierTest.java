package com.autumn.learn.java8.juc.cyclicbarrier;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author yl
 * @since 2022-11-12 14:16
 */
public class CyclicBarrierTest {

    public List<String> createThreadNames() {
        List<String> threadNames = new ArrayList<>();
        threadNames.add("小明");
        threadNames.add("小红");
        threadNames.add("小雪");
        return threadNames;
    }

    @Test
    public void testCyclicBarrierWithAction() {
        Runnable barrierAction = new BarrierAction();
        ThreadFactory threadFactory = new NamedThreadFactory(createThreadNames());
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, barrierAction); // 所有线程都每运行到达一个await拦截点时，执行一次barrierAction
        ExecutorService executorService = new ThreadPoolExecutor(4, 8, 0L, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>(5), threadFactory, new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < 3; i++) {
            executorService.execute(new CyclicBarrierThread(cyclicBarrier));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCyclicBarrierWithoutAction() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        ThreadFactory threadFactory = new NamedThreadFactory(createThreadNames());
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>(5), threadFactory, new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < 3; i++) {
            executorService.execute(new CyclicBarrierThread(cyclicBarrier));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class NamedThreadFactory implements ThreadFactory {
        private final List<String> threadNames;
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(0);

        public NamedThreadFactory(List<String> threadNames) {
            this.threadNames = threadNames;
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {

            Thread t = new Thread(group, r, this.threadNames.get(threadNumber.getAndIncrement()), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
