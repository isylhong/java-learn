package com.autumn.learn.network.nio.case2_mutilworker;

import com.autumn.learn.network.nio.MyNioWorker;
import com.autumn.learn.network.nio.MyWorkerGroup;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Acceptor的连接分发功能整合在Reactor对象中
 *
 * @author yl
 * @since 2022-11-12 15:08
 */
public class MyReactorAndAcceptor {
    private final MyWorkerGroup workerGroup;
    private final Selector selector;

    public MyReactorAndAcceptor(MyWorkerGroup workerGroup, Selector selector) {
        this.workerGroup = workerGroup;
        this.selector = selector;
    }

    public void start() {
        while (true) {
            try {
                selector.select(); // 线程阻塞，直到有新连接或注册在Selector的通道完成了IO操作才会被唤醒。返回值为目前完成了IO操作的通道数量。

                Set<SelectionKey> selectionKeys = selector.selectedKeys(); // 获取目前到达指定事件（如，完成了IO
                // 操作）的通道对应的SelectionKey

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) { // 遍历目前完成了IO操作的通道对应的SelectionKey
                    SelectionKey key = iterator.next();

                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();

                        SocketChannel socketChannel = server.accept(); // 创建Socket的连接通道

                        socketChannel.configureBlocking(false);

                        MyNioWorker worker = workerGroup.next();
                        worker.registry(socketChannel, SelectionKey.OP_READ);

                        // 注意：调用Selector.select()后会阻塞，直到注册在Selector中的有通道到达某个状态(如：连接准备好，通道可读等)后才会被唤醒。
                        // 由于worker线程调用select()时还没有channel被注册到Selector中，所以在select()
                        //方法调用后work线程将一直处于阻塞状态，无法通过后面注册的通道唤醒。
                        // 因此，在将第一个通道注册到Worker线程的Selector中后，需要手动调用Selector.wakeup()
                        //唤醒Selector，以便持续监听注册在该Selector中的通道事件。
                        worker.getSelector().wakeup();
                    }
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
