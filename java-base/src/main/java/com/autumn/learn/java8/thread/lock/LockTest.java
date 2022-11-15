package com.autumn.learn.java8.thread.lock;

import com.autumn.learn.java8.utils.ThreadUtil;
import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author yl
 * @since 2022-11-12 14:22
 */
public class LockTest {

    /**
     * writerA(running),  readerA读(阻塞) -> readerA读(阻塞) -> readerA读(阻塞)
     */
    @Test
    public void testRunWrite_WaitRead_WaitRead() throws InterruptedException {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                writeLock.lock();
                ThreadUtil.printInfoAtFixed("writing", 5000, 10);
                writeLock.unlock();
            }
        };

        Runnable readTask = new Runnable() {
            @Override
            public void run() {
                readLock.lock();
                ThreadUtil.printInfoAtFixed("reading", 1000, 2);
                readLock.unlock();
            }
        };


        new Thread(writeTask, "WriterA").start();

        ThreadUtil.sleep(500L);

        new Thread(readTask, "readerA").start();
//        new Thread(readTask, "readerB").start();
//        new Thread(readTask, "readerC").start();

        Thread.sleep(5 * 60 * 1000L);
    }

    /**
     * threadA写(running) threadA读(写降读，running) threadA读(写降读，running) threadB读(阻塞)
     */
    @Test
    public void testFairWriteLock() throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

        Runnable taskA = new Runnable() {
            @Override
            public void run() {
                lock.writeLock().lock();
                ThreadUtil.print("获取到写锁");
                ThreadUtil.printInfoAtFixed("writing...", 1000, 1);
                lock.readLock().lock(); // 写降读
                ThreadUtil.print("第1次获取到公平读锁");
                ThreadUtil.printInfoAtFixed("reading1...", 1000, 1);
                lock.readLock().lock();
                ThreadUtil.print("第2次获取到公平读锁");
                ThreadUtil.printInfoAtFixed("reading2...", 1000, 1);
//                ThreadUtil.print("释放读锁");
                lock.writeLock().unlock();
                ThreadUtil.print("释放写锁");
                ThreadUtil.printInfoAtFixed("taskA is running", 1000, 1);
            }
        };
        new Thread(taskA, "threadA").start();

        ThreadUtil.sleep(2000);

        Runnable taskB = new Runnable() {
            @Override
            public void run() {
                ThreadUtil.print("尝试获取读锁");
                lock.readLock().lock();
                ThreadUtil.print("获取到读锁");
                ThreadUtil.printInfoAtFixed("reading...", 1000, 2);
            }
        };
        new Thread(taskB, "threadB").start();

        Thread.sleep(10 * 60 * 1000L);
    }

    @Test
    public void testConditionWaitSignal() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Runnable taskA = new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    ThreadUtil.print("taskA获取到锁");
                    ThreadUtil.printInfoAtFixed("taskA running...", 1000, 1);
                    ThreadUtil.print("调用condition.wait()进入阻塞");
                    condition.await();
                    ThreadUtil.print("taskA被唤醒");
                    lock.unlock();
                    ThreadUtil.print("taskA释放锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable taskB = new Runnable() {
            @Override
            public void run() {
                lock.lock();
                ThreadUtil.print("taskB获取到锁");
                ThreadUtil.printInfoAtFixed("taskB running...", 1000, 2);
                ThreadUtil.print("唤醒taskA");
                condition.signal();
                try {
                    Thread.sleep(20 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
                ThreadUtil.print("taskB释放锁");
            }
        };

        new Thread(taskA).start();
        ThreadUtil.printInfoAtFixed("", 1000, 10);
        new Thread(taskB).start();

        Thread.sleep(2 * 60 * 1000L);
    }

    /**
     * Condition接口:
     * juc工具包中用于替换wait()、signal()实现线程间通信功能的类。
     *
     * 1、原生java
     * 通过synchronize、wait、signal实现threadA和threadB之间的通信。
     *
     * threadA流程：获取到锁obj -》 阻塞当前线程 -》 释放获取到的锁obj。
     * synchronize(obj){
     *   ...
     *   obj.wait();
     *   ...
     * }
     * threadB流程：获取到锁obj -》唤醒threadA -> 释放获取到的锁obj -》threadA再次获取到锁obj -》threadA继续运行。
     * synchronize(obj){
     *   ...
     *   obj.signal()
     *   ...
     * }
     *
     * 2、juc工具包
     * 通过ReentrantLock.lock()|unlock()、Condition.wait()、Condition.signal()实现threadA和threadB之间的通信。
     *
     * threadA流程：获取到锁myLock -》 阻塞当前线程 -》 释放获取到的锁myLock。
     * {
     *   ReentrantLock myLock = new ReentrantLock();
     *   myLock.lock();
     *   ...
     *   Condition.wait()
     *   ...
     *   myLock.unlock();
     * }
     *
     * threadB流程：获取到锁myLock -》唤醒threadA -> 释放获取到的锁myLock -》threadA再次获取到锁myLock -》threadA继续运行。
     * {
     *   ReentrantLock myLock = new ReentrantLock();
     *   myLock.lock();
     *   ...
     *   Condition.signal()
     *   ...
     *   myLock.unlock();
     * }
     */
    @Test
    public void testCondition() throws InterruptedException {
        final ReentrantLock myLock = new ReentrantLock();
        Condition myCondition = myLock.newCondition();
        Thread threadA = new Thread(() -> {
            myLock.lock();
            ThreadUtil.print("获取到myLock锁...");
            try {
                ThreadUtil.print("调用myCondition.await()进入阻塞状态");
                myCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myLock.unlock();
            ThreadUtil.print("释放myLock锁...");
        }, "threadA");
        threadA.start();
        Thread.sleep(1000L); // 保证threadA已运行，获取锁进入阻塞状态

        Thread threadB = new Thread(() -> {
            myLock.lock();
            ThreadUtil.print("获取到myLock锁...");
            ThreadUtil.print("调用myCondition.signal()唤醒阻塞线程");
            myCondition.signal();
            myLock.unlock();
            ThreadUtil.print("释放myLock锁...");
        }, "threadB");
        threadB.start();

        threadB.interrupt();
        Thread.sleep(5000L);

        ThreadUtil.print("主线程结束");
    }

    /**
     * Reentrant.acquireInterruptibly()
     * 当前线程获取锁失败被阻塞后，可被中断信号唤醒，并抛出InterruptedException
     */
    @Test
    public void testReentrantAcquireInterruptibly() throws InterruptedException {
        final ReentrantLock myLock = new ReentrantLock();

        Thread threadA = new Thread(() -> {
            myLock.lock();

            int i = 0;
            long nextExecuteTime = System.currentTimeMillis();
            do {
                long currentTimeMillis = System.currentTimeMillis();
                if (nextExecuteTime <= currentTimeMillis) {
                    ThreadUtil.print("运行中...");
                    nextExecuteTime = currentTimeMillis + 1000L;
                    i++;
                }
            } while (i <= 10);
            myLock.unlock();
        }, "threadA");
        threadA.start();
        Thread.sleep(1000L); // 保证threadA已运行，获取锁进入阻塞状态

        Thread threadB = new Thread(() -> {
            ThreadUtil.print("尝试获取锁");
            try {
                myLock.lockInterruptibly();
                boolean isInterrupted = Thread.currentThread().isInterrupted();
                String info = (isInterrupted ? "发现中断信号" : "为发现中断信号") + "，中断标志位为：" + isInterrupted;
                ThreadUtil.print(info);
            } catch (InterruptedException e) {
                e.printStackTrace();
                ThreadUtil.print("处理中断，中断标志位为：" + Thread.currentThread().isInterrupted());
            }
            ThreadUtil.print("释放锁");
            myLock.unlock(); // 不调用unlock()释放锁的话，threadA会一直处于阻塞(同步阻塞)并尝试获取锁
        }, "threadB");
        threadB.start();
        Thread.sleep(5000L); // 确保threadB已开始运行，并因获取不到锁而阻塞(同步阻塞)

        threadB.interrupt();

        Thread.sleep(15000L); // 让主线程睡眠，防止junit原因提前结束线程，影响其他线程对中断信号的响应效果
        ThreadUtil.print("主线程结束");
    }

    /**
     * 公平锁测试
     */
    @Test
    public void testReentrantLockFair() throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock(true);

        String[] threadNames = new String[]{"threadA", "threadB", "threadC"};
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    lock.lock();
                    ThreadUtil.print("");
                    lock.unlock(); // theadA释放锁再次尝试获取锁的时候，threadB、threadC已经因获取锁失败而阻塞。
                }
            }, threadNames[i]).start();
        }

        Thread.sleep(1000L);
    }

    /**
     * 非公平锁测试
     */
    @Test
    public void testReentrantLockNonFair() throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock(false);

        String[] threadNames = new String[]{"threadA", "threadB", "threadC"};
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    lock.lock();
                    ThreadUtil.print("");
                    lock.unlock();
                }
            }, threadNames[i]).start();
        }

        Thread.sleep(1000L);
    }
    

}
