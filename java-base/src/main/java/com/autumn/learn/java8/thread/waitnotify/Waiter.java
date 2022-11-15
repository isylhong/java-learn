package com.autumn.learn.java8.thread.waitnotify;

/**
 *
 * @author yl
 * @since 2022-11-12 14:52
 */
public class Waiter implements Runnable {
    private final Monitor monitor;
    private final Object obj;

    public Waiter(Monitor monitor, Object obj) {
        this.monitor = monitor;
        this.obj = obj;
    }

    @Override
    public void run() {
        try {
            String threadName = Thread.currentThread().getName();

            System.out.println(threadName + " are ready!");
            synchronized (monitor) {
                monitor.wait(); // 阻塞
            }

            System.out.println(threadName + " start running");
            //
            Thread.sleep(2000L); // 模拟线程执行

            System.out.println(threadName + " finish, notify one worker continue work.");

            if (monitor.isLastFinished()) {
                synchronized (obj) {
                    obj.notify();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
