package com.autumn.learn.java8.thread;

import com.autumn.learn.java8.utils.ThreadUtil;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在threadB中调用threadA.interrupt()后，threadA的中断标志为将由false改为true。并且当threadA响应中断后会重置中断标志为false。
 *
 * 及时响应中断信号的方法：
 * 1、wait()(抛异常)：等待阻塞 -----interrupt-----> 同步阻塞(获取同步锁后变为就绪态)
 * 2、sleep()(抛异常)：其他阻塞 -----interrupt-----> 就绪
 * 3、join()(抛异常)：其他阻塞 -----interrupt-----> 就绪
 * 4、LockSupport.park()(不抛异常)：同步阻塞 -----interrupt-----> 就绪
 *
 * 线程因以下情况进入阻塞(同步阻塞)时不会及时响应中断，但中断信号会被记录(通过设置中断标志位为true来记录)
 * 1、synchronized(monitor){...}
 * 2、ReentrantLock.lock()
 *
 * @author yl
 * @since 2022-11-12 14:44
 */
public class ThreadInterruptTest {

    /**
     * 不响应中断：情形一
     * ReentrantLock.lock()
     */
    @Test
    public void testReentrantLockInterrupt() throws InterruptedException {
        final ReentrantLock reentrantLock = new ReentrantLock();

        Thread threadA = new Thread(() -> {
            ThreadUtil.print("第1次尝试获取锁");
            reentrantLock.lock();
            ThreadUtil.print("第1次获取到锁，并持锁睡眠3s");
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ThreadUtil.print("释放第1次获取的锁");
            reentrantLock.unlock();

            int i = 0;
            long nextExecuteTime = System.currentTimeMillis();
            do {
                long currentTimeMillis = System.currentTimeMillis();
                if (nextExecuteTime <= currentTimeMillis) {
                    ThreadUtil.print("运行中...");
                    nextExecuteTime = currentTimeMillis + 500L;
                    i++;
                }
            } while (i <= 1);

            ThreadUtil.print("第2次尝试获取锁");
            reentrantLock.lock();
            ThreadUtil.print("第2次获取到锁");
            ThreadUtil.print("释放第2次获取锁");
            reentrantLock.unlock();
        }, "threadA");
        threadA.start();
        Thread.sleep(1000L); // 保证threadA已运行，获取锁进入阻塞状态

        Thread threadB = new Thread(() -> {
            ThreadUtil.print("尝试获取锁");
            reentrantLock.lock(); // 线程因调用ReentrantLock.lock()进入阻塞(同步阻塞)时不会及时响应中断，但中断信号会被记录(通过设置中断标志位为true来记录)
            ThreadUtil.print("获取到锁");
            try {
                boolean isInterrupted = Thread.currentThread().isInterrupted();
                String info = (isInterrupted ? "发现中断信号" : "为发现中断信号") + "，中断标志位为：" + isInterrupted;
                ThreadUtil.print(info);
                TimeUnit.SECONDS.sleep(1000L); // 线程调用sleep后，因发现中断信号为true，而响应中断
            } catch (InterruptedException e) {
                e.printStackTrace();
                ThreadUtil.print("处理中断，中断标志位为：" + Thread.currentThread().isInterrupted());
            }
            ThreadUtil.print("释放锁");
//            reentrantLock.unlock(); // 不调用unlock()释放锁的话，threadA会一直处于阻塞(同步阻塞)并尝试获取锁
        }, "threadB");
        threadB.start();
        Thread.sleep(1000L); // 确保threadB已开始运行，并因获取不到锁而阻塞(同步阻塞)

        threadB.interrupt();

        Thread.sleep(5000L); // 让主线程睡眠，防止junit原因提前结束线程，影响其他线程对中断信号的响应效果
        ThreadUtil.print("主线程结束");
    }

    /**
     * 不响应中断：情形二
     * synchronized(monitor){...}
     */
    @Test
    public void testSynchronizeInterrupt() throws InterruptedException {
        final Object lock = new Object();

        Thread threadA = new Thread(() -> {
            ThreadUtil.print("第1次尝试获取锁");
            synchronized (lock) {
                ThreadUtil.print("第1次获取到锁，并持锁睡眠3s");
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ThreadUtil.print("释放第1次获取的锁");
            }

            int i = 0;
            long nextExecuteTime = System.currentTimeMillis();
            do {
                long currentTimeMillis = System.currentTimeMillis();
                if (nextExecuteTime <= currentTimeMillis) {
                    ThreadUtil.print("运行中...");
                    nextExecuteTime = currentTimeMillis + 500L;
                    i++;
                }
            } while (i <= 1);

            ThreadUtil.print("第2次尝试获取锁");
            synchronized (lock) {
                ThreadUtil.print("第2次获取到锁");
                ThreadUtil.print("释放第2次获取锁");
            }
        }, "threadA");
        threadA.start();
        Thread.sleep(1000L); // 保证threadA已运行，获取锁进入阻塞状态

        Thread threadB = new Thread(() -> {
            ThreadUtil.print("尝试获取锁");
            synchronized (lock) {
                ThreadUtil.print("获取到锁");
                try {
                    boolean isInterrupted = Thread.currentThread().isInterrupted();
                    String info = (isInterrupted ? "发现中断信号" : "为发现中断信号") + "，中断标志位为：" + isInterrupted;
                    ThreadUtil.print(info);
                    // 线程因synchronize(){...}进入阻塞(同步阻塞)时不会及时响应中断，但中断信号会被记录(通过设置中断标志位为true来记录)
                    TimeUnit.SECONDS.sleep(1000L); // 线程调用sleep后，因发现中断信号为true，而响应中断
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    ThreadUtil.print("处理中断，中断标志位为：" + Thread.currentThread().isInterrupted());
                }
                ThreadUtil.print("释放锁");
//                while (true); // 模拟threadB不释放获取到的锁，此时threadA会一直处于阻塞(同步阻塞)并尝试获取锁
            }
        }, "threadB");
        threadB.start();
        Thread.sleep(1000L); // 确保threadB已开始运行，并因获取不到锁而阻塞(同步阻塞)

        threadB.interrupt();

        Thread.sleep(5000L); // 让主线程睡眠，防止junit原因提前结束线程，影响其他线程对中断信号的响应效果
        ThreadUtil.print("主线程结束");
    }

    /**
     * 响应中断：情形一
     * LockSupport.lock()，不抛异常
     */
    @Test
    public void testLockSupportInterrupt() throws InterruptedException {
        Thread threadA = new Thread(() -> {
            ThreadUtil.print("threadA开始运行");
            ThreadUtil.print("调用LockSupport.park()进入阻塞状态");
            LockSupport.park();
            String msg = Thread.currentThread().isInterrupted() ? "因中断而提前结束阻塞" : "调用LockSupport.unpark()获取到锁介素阻塞";
            ThreadUtil.print(msg + "，中断标志位: " + Thread.currentThread().isInterrupted());
        }, "threadA");
        threadA.start();

        Thread.sleep(2000L); // 确保threadA已经开始运行了
        ThreadUtil.print("准备向threadA发送中断信号，threadA的中断标志位为：" + threadA.isInterrupted());
        threadA.interrupt();
        ThreadUtil.print("向threadA发送中断信号后，threadA的中断标志位为：" + threadA.isInterrupted());
//        LockSupport.unpark(threadA);

        Thread.sleep(3000L); // 让主线程睡眠，防止junit原因提前结束线程，影响其他线程对中断信号的响应效果
        ThreadUtil.print("主线程结束");
    }

    /**
     * 响应中断：情形二
     * threadA.join()，抛异常。
     * 因join()方法进入阻塞状态的线程会响应中断信号，被join的线程不会响应中断且继续运行。
     * threadB中调用threadA.join()，threadB 进入阻塞(其他阻塞) -> ThreadB调用interrupt，产生中断信号 -> threadB响应中断信号，进入就绪状态。
     *
     * main                 threadA            threadB
     * running
     * -                    running
     * -                    -                  running
     * -                    -                  threadA.join()
     * -                    running            block(其他阻塞)
     * running              -                  block(其他阻塞)
     * threadB.interrupt()  -
     * -                    -                  running，响应中断，处理中断
     * -                    running            结束线程
     * running              -
     * 结束线程             因main线程结束而终止
     */
    @Test
    public void testJoinInterrupt() throws InterruptedException {
        ThreadUtil.print("启动主线程");

        final Thread threadA = new Thread(() -> {
            ThreadUtil.print("threadA开始运行");
            long nextExecuteTime = System.currentTimeMillis() + 500L;
            while (true) {
                long currentTimeMillis = System.currentTimeMillis();
                if (nextExecuteTime <= currentTimeMillis) {
                    ThreadUtil.print("继续运行");
                    nextExecuteTime = currentTimeMillis + 500L;
                }
            }
        }, "threadA");
        threadA.start();
        Thread.sleep(1000L); // 确保threadA已经开始运行了

        Thread threadB = new Thread(() -> {
            try {
                ThreadUtil.print("启动threadB");
                ThreadUtil.print("join threadA，进入阻塞状态(其他阻塞)");
                threadA.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                ThreadUtil.print("处理中断异常");
            }
            ThreadUtil.print("结束线程");
        }, "threadB");
        threadB.start();

        Thread.sleep(3000L); // 确保threadB已经开始运行了
        ThreadUtil.print("向threadB发送中断信号");
        threadB.interrupt(); // 向线程threadB发送中断信号

        Thread.sleep(2000L); // 让主线程睡眠，防止junit原因提前结束线程，影响其他线程对中断信号的响应效果
        ThreadUtil.print("主线程结束");
    }

    /**
     * 响应中断：情形三
     * Object.wait()，因wait()方法进入等待阻塞的线程会响应中断信号
     * 调用wait，进入阻塞(等待阻塞) -> 调用interrupt，产生中断信号 -> 响应中断信号，进入阻塞状态(同步阻塞，因未获取到锁而阻塞) -> 获取到同步锁，进入就绪状态
     */
    @Test
    public void testWaitInterrupt() throws InterruptedException {
        final Object lock = new Object();
        Thread threadA = new Thread(() -> {
            synchronized (lock) {
                try {
                    ThreadUtil.print("获取到同步监视器，调用wait()进入等待状态");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    ThreadUtil.print("中断异常处理");
                }
            }
            ThreadUtil.print("执行结束");
        }, "threadA");
        threadA.start();

        Thread.sleep(1000L);

        // 向线程threadA发送中断信号
        ThreadUtil.print("向threadA发送中断信号");
        threadA.interrupt();

        Thread.sleep(5000L); // 让主线程睡眠，防止junit原因提前结束线程，影响其他线程对中断信号的响应效果
        ThreadUtil.print("主线程结束");
    }

    /**
     * 不响应中断：情形四
     * Thread.sleep()，抛异常。
     * 因sleep()方法进入其他阻塞的线程会响应中断信号。
     * 调用sleep，进入阻塞状态(等待阻塞)  -> 调用interrupt，产生中断信号 -> 响应中断信号，进入就绪状态
     */
    @Test
    public void testSleepInterrupt() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                ThreadUtil.print("调用sleep()开始睡眠，进入等待状态");
                Thread.sleep(3000L);
                ThreadUtil.print("发现中断信号");
            } catch (InterruptedException e) {
                e.printStackTrace();
                ThreadUtil.print("中断异常处理");
            }
            ThreadUtil.print("执行结束");
        }, "threadA");
        thread.start();

        Thread.sleep(1000L);

        // 向线程threadA发送中断信号
        ThreadUtil.print("向threadA发送中断信号");
        thread.interrupt();

        Thread.sleep(5000L); // 让主线程睡眠，防止junit原因提前结束线程，影响其他线程对中断信号的响应效果
        ThreadUtil.print("主线程结束");
    }

    /**
     * 线程中断信号不会影响(中断)处于运行态的线程
     */
    @Test
    public void testWhileInterrupt() throws InterruptedException {
        Thread thread = new Thread(() -> {
            ThreadUtil.print("无限循环处理");
            while (true) ;
        }, "threadA");
        thread.start();

        Thread.sleep(1000L);

        // 向线程threadA发送中断信号
        ThreadUtil.print("向threadA发送中断信号");
        thread.interrupt();

        Thread.sleep(5000L); // 让主线程睡眠，防止junit原因提前结束线程，影响其他线程对中断信号的响应效果
    }
    
}
