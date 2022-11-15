package com.autumn.learn.network.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * NIO模式下，工作线程A与Stream之间有一个ChannelA。工作线程读写数据是与Channel关联的缓存进行存取。
 * Channel会注册到一个Selector中，通过轮询所有注册在Selector中的Channel，查询所有已准备好了数据的Channel，然后让工作线程处理Channel中已经准备好了的数据。
 * 如果ChannelA在还在准备数据，工作线程A还未分配去处理ChannelA中的数据，此时就可以让工作线程A去处理另一个已经准备好数据了的ChannelB。
 *
 * @author yl
 * @since 2022-11-12 15:12
 */
public class MyNioWorker {
    private final String workerName;
    private final Selector selector;
    private final LinkedBlockingDeque<SocketChannel> socketChannels;
    private final Executor executor;
    private final StringBuilder sb = new StringBuilder();
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    private boolean isStarted = false;


    public MyNioWorker(String workerName, Selector selector, Executor executor) {
        this.workerName = workerName;
        this.selector = selector;
        this.socketChannels = new LinkedBlockingDeque<>();
        this.executor = executor;
    }

    public Selector getSelector() {
        return selector;
    }

    public void registry(SocketChannel socketChannel, int ops) {
//        System.out.println(this.workerName + " - " + this.selector + " " + socketChannel.socket());

        /*
        为什么不直接把注册操作放在这里？
        答：把注册放在这里做的话，用的是主线程处理，这样主线程需要花费大量时间去处理注册操作，从而降低创建socket连接的效率。
            好的做法是把注册操作放到Worker线程中去处理。
         */
        // socketChannel.register(selector, SelectionKey.OP_READ); // 把注册放在这里做的话，用的是主线程处理，而不是Worker线程处理

        socketChannels.add(socketChannel);

        if (!isStarted) {
            isStarted = true;
            executor.execute(this::run);
        }
    }

    public void run() {
        while (true) {
            try {
                int select = selector.select();
                if (select > 0) { // // 处理已经注册在Selector中，且数据准好了的通道
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        processKey(key);
                    }
                } else { // 将新通道注册到Selector中
                    while (!socketChannels.isEmpty()) {
                        SocketChannel channel = socketChannels.take();
                        System.out.println(workerName + " 新通道注册 " + channel);
                        channel.register(selector, SelectionKey.OP_READ);
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void processKey(SelectionKey key) {
        int len = 0;
        try {
            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();

                //                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                while ((len = socketChannel.read(byteBuffer)) != -1) {
                    if (len > 0) {
                        sb.append(new String(byteBuffer.array(), 0, len, StandardCharsets.UTF_8));
                        byteBuffer.clear();
                    } else {
                        break;
                    }
                }
                System.out.println("收到客户端消息：" + sb);
                if (sb.toString().equals("bye")) {
                    System.out.println(workerName + " 断开通道连接 " + socketChannel.socket());
                    socketChannel.close();
                }
                sb.delete(0, sb.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
