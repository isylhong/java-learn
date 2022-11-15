package com.autumn.learn.java8.thread;

import org.junit.Test;

public class ThreadTest {
    @Test
    public void testDaemonThread() {
        Thread t1 = new Thread(() -> {
            // 创建线程，校验守护线程内创建线程是否为守护线程
            Thread t2 = new Thread(() -> {
                System.out.println(getThreadName() + "has started and is" + (Thread.currentThread().isDaemon() ? "守护线程" : "非守护线程"));
            }, "t2");
            t2.start();

            // 当所有用户线程执行完，守护线程会被直接杀掉，程序停止运行
            int i = 1;
            while (true) {
                try {
                    System.out.println(getThreadName() + "starting sleep...");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getThreadName() + (Thread.currentThread().isDaemon() ? " 守护线程" : " 非守护线程") + " , 执行次数 : " + i);
                if (i++ >= 10) {
                    break;
                }
            }
        }, "t1");
        // setDaemon(true) 必须在 start() 之前设置，否则会抛出IllegalThreadStateException异常，该线程仍默认为用户线程，继续执行
        t1.setDaemon(true);
        t1.start();

        try {
            System.out.println(getThreadName() + " starting sleep...");
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 主线程结束
        System.out.println("主线程结束");
    }

    public String getThreadName() {
        return "[" + Thread.currentThread().getName() + "] : ";
    }
}
