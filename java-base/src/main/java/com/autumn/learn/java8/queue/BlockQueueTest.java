package com.autumn.learn.java8.queue;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 队尾添加元素：add()、offer()、put()
 * 获取队头元素并删除：remove()、poll()、take()
 * 获取队头元素不删除：element()、peek()
 *
 * @author yl
 * @since 2022-11-12 13:14
 */
public class BlockQueueTest {
    /**
     * 执行失败抛异常组合：add()、remove()、element()
     * add：队满，添加失败，抛异常(IllegalStateException)。
     * remove：队列为空，获取失败，抛异常(NoSuchElementException)。
     * element()：队列为空，获取失败，抛异常(NoSuchElementException)。
     */
    @Test
    public void testAddRemoveElement() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

        boolean isSuccess = queue.add("item1");
        System.out.println(isSuccess);
//        queue.add("item1"); // IllegalStateException: Queue full

        String element = queue.element();
        System.out.println(element);

//        String remove = queue.remove();
//        System.out.println(remove);
//        queue.remove(); // NoSuchElementException

        queue.element(); // NoSuchElementException
    }

    /**
     * 执行失败不抛异常组合：offer()、poll()、peek()
     * offer()：// 队满，添加失败，返回false。
     * poll()：// 队列为空，获取失败，返回null。
     * peek()：// 队列为空，获取失败，返回null。
     */
    @Test
    public void testOfferPollPeek() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

        boolean isSuccess = queue.offer("item1");
        System.out.println(isSuccess);
        System.out.println(queue.offer("item2")); // 队满，添加失败，返回false

        String peek = queue.peek();
        System.out.println(peek);

        String poll = queue.poll();
        System.out.println(poll);
        System.out.println(queue.poll()); // 队列为空，获取失败，返回null

        System.out.println(queue.peek()); // 队列为空，获取失败，返回null
    }

    /**
     * 阻塞，可能抛异常组合：put()、take()
     * put()：队满，阻塞，直到队列有空间存放新增元素
     * take()：队空，阻塞，直到队列元素可获取
     */
    @Test
    public void testPutTake() {
        final Step step = new Step();
        final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

        TimerTask takeTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    String item = queue.take(); // ②
                    System.out.println(step.stepTo("take <" + item + ">"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        TimerTask putTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    queue.put("item3"); // ⑤
                    System.out.println(step.stepTo("put <item3>"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Timer().schedule(takeTask, 1000L);
        new Timer().schedule(putTask, 3000L);

        try {
            queue.put("item1"); // ①
            System.out.println(step.stepTo("put <item1>"));

            queue.put("item2"); // 队满，阻塞，直到队列有空间存放新增元素 ③
            System.out.println(step.stepTo("put <item2>"));


            String take = queue.take(); // ④
            System.out.println(step.stepTo("take <" + take + ">"));

            System.out.println(step.stepTo("take <" + queue.take() + ">")); // 队空，阻塞，直到队列元素可获取 ⑥
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static class Step {
        private final AtomicInteger count = new AtomicInteger(0);

        String stepTo(String info) {
            return "第" + count.incrementAndGet() + "步: " + info;
        }
    }
}
