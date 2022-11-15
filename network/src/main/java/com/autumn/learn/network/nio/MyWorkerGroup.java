package com.autumn.learn.network.nio;

import com.autumn.learn.network.nio.MyNioWorker;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Worker线程池
 *
 * @author yl
 * @since 2022-11-12 15:13
 */
public class MyWorkerGroup {
    private static final AtomicInteger idx = new AtomicInteger(0);
    private static int threadNum;
    private static final List<MyNioWorker> workers = new ArrayList<>();

    public MyWorkerGroup(int nThread)  {
        threadNum = nThread;
        for (int i = 1; i <= nThread; i++) {
            Selector selector = null; // 每个Worker线程都应有数据一个独属于自己的Selector对象。
            try {
                selector = Selector.open();
                MyNioWorker worker = new MyNioWorker("worker" + i, selector, new MyEventExecutor());
                workers.add(worker);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public MyNioWorker next() {
        return workers.get(idx.getAndIncrement() % threadNum);
    }

    class MyEventExecutor implements Executor {
        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }
}
