package com.autumn.learn.java8.juc.phaser;

/**
 *
 * @author yl
 * @since 2022-11-12 14:23
 */
public class PhaserTest {
    // 注意，不要用junit去测试。主线程中不使用 while (!myPhaser.isTerminated()) {}时，线程会提前结束，达不到预期效果。
    public static void main(String[] args) {
        MyPhaser myPhaser = new MyPhaser();
        myPhaser.bulkRegister(3);
        System.out.println(myPhaser.getRegisteredParties());

        Thread thread1 = new Thread(new PhaserTasks.XiaoMeiTask(myPhaser));
        thread1.setName("小美");
        thread1.start();

        Thread thread2 = new Thread(new PhaserTasks.XiaoMingTask(myPhaser));
        thread2.setName("小明");
        thread2.start();

        Thread thread3 = new Thread(new PhaserTasks.XiaoXueTask(myPhaser));
        thread3.setName("小雪");
        thread3.start();

        while (!myPhaser.isTerminated()) {
            System.out.println(myPhaser.getRegisteredParties());
        }
        System.out.println("完成所有事情，大家一起开始吃饭");
    }
}
