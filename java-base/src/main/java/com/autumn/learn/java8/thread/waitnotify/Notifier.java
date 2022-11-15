package com.autumn.learn.java8.thread.waitnotify;

/**
 *
 * @author yl
 * @since 2022-11-12 14:52
 */
public class Notifier implements Runnable {
    private final Monitor monitor;
    private final Object obj;

    public Notifier(Monitor monitor, Object obj) {
        this.monitor = monitor;
        this.obj = obj;
    }

    @Override
    public void run() {
        try {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " begin run");

            Thread.sleep(1000L); // 模拟线程执行

            System.out.println(threadName + " notify other worker and waiting done.");
            synchronized (monitor) {
                monitor.notifyAll();
            }
            synchronized (obj) {
                obj.wait();
            }
            System.out.println(threadName + " wake up, and continue run...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
