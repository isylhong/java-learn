package com.autumn.learn.java8.juc.semaphore;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 *
 * @author yl
 * @since 2022-11-12 14:35
 */
public interface SemaphoreTasks {

    class ThreadFirst extends Thread {
        private final List<Semaphore> semaphores;

        public ThreadFirst(List<Semaphore> semaphores) {
            this.semaphores = semaphores;
        }

        @Override
        public void run() {
            try {
                Semaphore semaphore0 = semaphores.get(0); // 获取信号量集0
                semaphore0.acquire(); // 从信号量集0获取一个信号
                Thread.sleep(1000L);
                System.out.println("ThreadA...");
                semaphores.get(1).release(); // 为信号量集1增一个信号
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    class ThreadSecond extends Thread {
        private final List<Semaphore> semaphores;

        public ThreadSecond(List<Semaphore> semaphores) {
            this.semaphores = semaphores;
        }

        @Override
        public void run() {
            try {
                Semaphore semaphore1 = semaphores.get(1); // 获取信号量集1一个信号
                semaphore1.acquire(); // 从信号量集1获取一个信号
                Thread.sleep(1000L);
                System.out.println("ThreadB...");
                semaphores.get(2).release();  // 为信号量集2增一个信号
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class ThreadThird extends Thread {
        private final List<Semaphore> semaphores;

        public ThreadThird(List<Semaphore> semaphores) {
            this.semaphores = semaphores;
        }

        @Override
        public void run() {
            try {
                Semaphore semaphore2 = semaphores.get(2); // 获取信号量集2
                semaphore2.acquire(); // 从信号量集2获取一个信号
                Thread.sleep(1000L);
                System.out.println("ThreadC...");
                semaphore2.release(); // 信号量集2释放一个信号
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
