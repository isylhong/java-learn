package com.autumn.learn.java8.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author yl
 * @since 2022-11-12 14:03
 */
public class ThreadUtil {
    /**
     * 打印线程信息
     * @param msg
     */
    public static void print(String msg) {
        Thread thread = Thread.currentThread();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeStr = sdf.format(new Date());
        System.out.println(timeStr + " [" + thread.getName() + "] " + msg);
    }

    /**
     * 固定时间间隔打印一次信息
     * @param msg
     * @param duration
     * @param sec
     */
    public static void printInfoAtFixed(String msg, int duration, int sec) {
        int i = 0;
        long nextExecuteTime = System.currentTimeMillis();
        long currentTimeMillis;
        while (true) {
            currentTimeMillis = System.currentTimeMillis();
            if (nextExecuteTime <= currentTimeMillis) {
                if (i >= sec)
                    break;
                ThreadUtil.print(msg);
                nextExecuteTime = currentTimeMillis + duration;
                i++;
            }
        }
    }

    /**
     * while循环模拟线程sleep
     * @param millis
     */
    public static void sleep(long millis) {
        int i = 0;
        long breakTime = System.currentTimeMillis() + millis;
        while (System.currentTimeMillis() <= breakTime) ;
    }
}
