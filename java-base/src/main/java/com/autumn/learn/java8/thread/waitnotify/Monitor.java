package com.autumn.learn.java8.thread.waitnotify;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author yl
 * @since 2022-11-12 14:52
 */
public class Monitor {
    private final AtomicInteger index;
    private final int part;

    public Monitor(int part) {
        this.part = part;
        index = new AtomicInteger(0);
    }

    public boolean isLastFinished() {
        return index.incrementAndGet() == part;
    }

    public boolean isFinish() {
        return index.intValue() == part;
    }
}