package com.autumn.learn.network.nio.case3_mutilworker;

import com.autumn.learn.network.nio.MyNioWorker;
import com.autumn.learn.network.nio.MyWorkerGroup;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Acceptor对象：
 * 与Reactor共用一个线程。有一个ServerSocketChannel和一个Worker线程组。
 * 每有一个新socket连接准备好时，MyAcceptor对象就会调用一次doDispatch()方法，用于将这个连接准备好的socket注册到Worker线程中。
 *
 * @author yl
 * @since 2022-11-12 15:10
 */
public class MyAcceptor {
    private final MyWorkerGroup workerGroup;
    private final ServerSocketChannel serverSocketChannel;

    public MyAcceptor(MyWorkerGroup workerGroup, ServerSocketChannel serverSocketChannel) {
        this.workerGroup = workerGroup;
        this.serverSocketChannel = serverSocketChannel;
    }

    /**
     * 每有一个新socket连接准备好时，MyAcceptor对象就会调用一次doDispatch()方法，用于将这个连接准备好的socket注册到Worker线程中。
     */
    public void doDispatch() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept(); // 创建Socket的连接通道

            socketChannel.configureBlocking(false);

            MyNioWorker worker = workerGroup.next();
            worker.registry(socketChannel, SelectionKey.OP_READ);

            // 注意：调用Selector.select()后会阻塞，直到注册在Selector中的有通道到达某个状态(如：连接准备好，通道可读等)后才会被唤醒。
            // 由于worker线程调用select()时还没有channel被注册到Selector中，所以在select()方法调用后work线程将一直处于阻塞状态，无法通过后面注册的通道唤醒。
            // 因此，在将第一个通道注册到Worker线程的Selector中后，需要手动调用Selector.wakeup()唤醒Selector，以便持续监听注册在该Selector中的通道事件。
            worker.getSelector().wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
