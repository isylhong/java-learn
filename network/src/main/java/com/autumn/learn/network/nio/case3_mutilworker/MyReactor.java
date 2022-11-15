package com.autumn.learn.network.nio.case3_mutilworker;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * Reactor对象：
 *   单线程，专门用于处理新的socket连接。
 *   每有一个新socket连接准备好时，MyAcceptor对象就会调用一次doDispatch()方法，将这个连接准备好的socket注册到Worker线程中。
 *
 * @author yl
 * @since 2022-11-12 15:11
 */
public class MyReactor {
    private final Selector selector;

    public MyReactor(Selector selector) {
        this.selector = selector;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                selector.select(); // 线程阻塞，直到有新连接准备好了才会被唤醒。
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    dispatch(key);
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void dispatch(SelectionKey key) {
        // 在serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, acceptor)语句中给serverSocketChannel的SelectionKey绑定了一个Acceptor对象，
        // 所以可以在这里通过SelectionKey.attachment()获取到Acceptor这个对象。
        MyAcceptor myAcceptor = (MyAcceptor) key.attachment(); // 获取与SelectionKey绑定的对象。
        if (myAcceptor != null) {
            myAcceptor.doDispatch(); // 手动调用Acceptor.dispatch方法，该方法中会将准备好的连接注册到Worker线程中。
        }
    }
}
