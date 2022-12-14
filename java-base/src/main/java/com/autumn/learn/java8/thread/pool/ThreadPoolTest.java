package com.autumn.learn.java8.thread.pool;

import com.autumn.learn.java8.utils.NumberUtil;
import com.autumn.learn.java8.utils.ThreadUtil;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author yl
 * @since 2022-11-12 14:49
 */
public class ThreadPoolTest {

    /**
     * 使用不同二进制值表示的ThreadPoolExecutor各状态
     */
    @Test
    public void testThreadPoolExecutorState() {
        int COUNT_BITS = Integer.SIZE - 3;

        int CAPACITY = (1 << COUNT_BITS) - 1;
        System.out.println("CAPACITY    " + NumberUtil.toBinaryString32(CAPACITY)); // CAPACITY与ctl
        // 做&运算，可计算线程池目前已开启了的Worker线程数
        System.out.println("~CAPACITY   " + NumberUtil.toBinaryString32(~CAPACITY)); // ~CAPACITY与ctl做&运算，可计算线程池的状态

        // 二进制指定N个高bit位用于表示线程池的5种状态
        int RUNNING = -1 << COUNT_BITS;
        int SHUTDOWN = 0 << COUNT_BITS;
        int STOP = 1 << COUNT_BITS;
        int TIDYING = 2 << COUNT_BITS;
        int TERMINATED = 3 << COUNT_BITS;
        System.out.println("RUNNING     " + NumberUtil.toBinaryString32(RUNNING));
        System.out.println("SHUTDOWN    " + NumberUtil.toBinaryString32(SHUTDOWN));
        System.out.println("STOP        " + NumberUtil.toBinaryString32(STOP));
        System.out.println("TIDYING     " + NumberUtil.toBinaryString32(TIDYING));
        System.out.println("TERMINATED  " + NumberUtil.toBinaryString32(TERMINATED));

        /*
        初始化线程池，ctl值初始化为：11100000 00000000 00000000 00000000，表示线程池处于RUNNING状态，开启的worker线程数为0。
        线程池启动后，只要线程池处于运行状态，ctl的高3bit位就是111，
        也就是说：ctl值为负数，可以推断出线程池处于RUNNING状态；线程池处于RUNNING状态，也可以推断出ctl值为负数。
         */
        int ctl = RUNNING | 0;
        System.out.println("线程池初始化，ctl值初始化为:         " + NumberUtil.toBinaryString32(ctl));
        int runState = RUNNING & ~CAPACITY;
        System.out.println("runStateOf(c & ~CAPACITY):      " + NumberUtil.toBinaryString32(runState));
        int workerCount = ctl & CAPACITY;
        System.out.println("workerCountOf(ctl & CAPACITY):    " + NumberUtil.toBinaryString32(workerCount));

        // 1+3位
        // 1100 1011->1100
        // 1111 1000->1001

        System.out.println("-1:     " + NumberUtil.toBinaryString32(-1));
        int bit_count = -1 >>> 1;
        System.out.println("-1>>>1: " + NumberUtil.toBinaryString32(bit_count));
    }

    /**
     *线程池中线程中断问题
     *
     * 假设线程池中只有一个threadA，任务队列中有task1，task2，task3，且threadA已经获取到task1并处于运行态。
     * 问：
     *  1、如果这是向threadA发送一个中断信号，线程会被中断运行吗？
     *  答：不会。
     *      threadA.interrupt()方法只是向threadA发送一个中断信号，并置threadA的中断表示位为true。因此，无法通过threadA.interrupt()让一个处于运行的task及时停止运行。
     *      想要通过中断来停止一个运行中的task，正确的做法是在task代码中手动调用Thread.isInterrupted()
     *      去检查中断标志位是否为ture以确定当前线程是否有中断信号。如果有中断信号，则需手动终止线程运行。
     *
     *  2、向运行中的threadA发送一个中断信号后，task1并未停止运行，threadA中断标志位被设置为true了。那么，threadA的中断标志是在什么时候被重置为false的？
     *  答：threadA的中断标志是在threadA向线程池任务队列（1个BlockQueue）获取任务（getTask()方法）的时候被重置为false的。
     *      workQueue.take()方法在获取task的时候会先通过ReentrantLock.lockInterruptibly()上锁，而上锁的过程会检查当前访问线程是否有中断信号(中断标志位是否位true)，
     *      如果当前访问线程有中断信号，则重置当前线程中断标志位为false，并抛出InterruptedException。此时，便可在getTask()方法中捕获这个中断异常。
     *
     *  3、向正在运行task1的threadA发送一个中断信号，threadA会继续获取任务队列中的task2、task3来运行吗？
     *  答：会。
     *      向运行中的threadA发送一个中断信号(threadA的中断标志位被设置为true)，threadA会在运行完task1再次调用getTask()获取任务的时候处理中断
     *      (重置threadA的中断标志位为false)。然后继续调用getTask()获取task2来执行。
     *
     */
    @Test
    public void testFutureTask() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        final AtomicInteger count = new AtomicInteger(1);
        Callable<String> callable = () -> {
            ThreadUtil.print("1开始运行");
            int i = 0;
            long nextExecuteTime = System.currentTimeMillis();
            do {
                long currentTimeMillis = System.currentTimeMillis();
                if (nextExecuteTime <= currentTimeMillis) {
                    ThreadUtil.print(" i'm running...");
                    nextExecuteTime = currentTimeMillis + 1000L;
                    i++;
                }
            } while (i <= 4);
            ThreadUtil.print("1结束运行");
            return "result-" + count.getAndIncrement();
        };

        Future<String> future = executorService.submit(callable);

        Thread.sleep(2000L); // 确保callable已经运行了
        ThreadUtil.print("调用cancel()尝试取消处于运行中的future task1");
        future.cancel(true);  // cancel无法停止一个已处于运行态的线程，只能等待线程运行完自己终止

        try {
            System.out.println(future.get()); // 取消一个运行中的task后，当前线程调用future.get()会报CancellationException异常
        } catch (CancellationException e) { // 不捕获这个异常会造成当前异常因异常停止运行，进而提前终止callable的运行
            System.out.println("发现任务被取消，调用get()方法的线程抛出CancellationException异常，但线程实际并未停止，会运行直到结束");
        }


        Thread.sleep(20 * 1000L);
        ThreadUtil.print("主线程结束运行！");
    }

    @Test
    public void testThreadPoolExecutorCreate() {
        ExecutorService executorService = new ThreadPoolExecutor(0, 1, 1L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        Runnable runnable = () -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("I'm a quick task");
//            while (true) ;
        };
        for (int i = 0; i < 1; i++) {
            executorService.submit(runnable);
        }

        executorService.shutdown(); // 线程池调用shutdown方法后将不再接收执行新提交的线程任务，但会让已提交到线程池中的线程执行完。
        try {
            // 线程池调用awaitTermination方法后会阻塞该线程，直到线程池中所有线程执行完。设置一个等待时间，
            // 返回true: 在等待时间内线程池内所有task都执行完。
            // 返回false: 超过等待时间后，线程池内存在未执行完task。同时强制结束未执行完的task。
            boolean isFinishAll = executorService.awaitTermination(2000, TimeUnit.MILLISECONDS); // false, 未执行完任务便关闭线程池
//            executorService.awaitTermination(3000, TimeUnit.MILLISECONDS); // false, 现象怪异
//            executorService.awaitTermination(5000, TimeUnit.MILLISECONDS); // true, 所有任务均执行完，关闭线程池
            System.out.println(isFinishAll);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
