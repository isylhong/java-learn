package com.autumn.learn.network.nio.case1_oneworker;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * Reactor单线程模型，指的是Reactor、Acceptor、Worker三角色的操作都由同一个线程完成，这个线程的职责如下：
 * 1）作为NIO服务端，接收客户端的TCP连接；
 * 2）作为NIO客户端，向服务端发起TCP连接；
 * 3）读取通信对端的请求或者应答消息；
 * 4）向通信对端发送消息请求或者应答消息。
 *
 * @author yl
 * @since 2022-11-12 15:15
 */
public class NioServerOneThread {

    public static void main(String[] args) throws IOException {
        int len;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        StringBuilder sb = new StringBuilder();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket serverSocket = serverSocketChannel.socket(); // 通过通道ServerSocketChannel获取一个ServerSocket
        serverSocket.bind(new InetSocketAddress(50001)); // ServerSocket绑定监听50001端口

        serverSocketChannel.configureBlocking(false); // 设置通道为非阻塞
        Selector selector = Selector.open(); // 创建一个Selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 将ServerSocketChannel注册到Selector
        // 中，并设置监听Socket连接准备好事件

        System.out.println("服务启动成功!");
        while (true) {
            int select = selector.select(); // 此方法调用后，会阻塞当前线程，直到注册在Selector的通道有完成了IO操作才会被唤醒。返回值为目前完成了IO操作的通道数量。

            Set<SelectionKey> selectionKeys = selector.selectedKeys(); // 获取目前到达指定事件（如，完成了IO操作）的通道对应的SelectionKey

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) { // 遍历目前完成了IO操作的通道对应的SelectionKey
                SelectionKey key = iterator.next();

                // Socket Channel处理流程：
                // 1、Acceptable状态(客户端有新Socket准备好可以连接时)：创建Socket的连接通道，并将这个通过注册到Selector中。
                // 2、Readable状态(Socket完成流数据传输)：到达Readable状态，创建工作线程去处理这些数据。

                if (key.isAcceptable()) { // 处理Acceptable状态Channel
                    //每当客户端有新Socket连接事件准备就绪，ServerSocketChannel中都会有Acceptable事件，故会多次获取同一个ServerSocketChannel对象
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();

                    SocketChannel socketChannel = server.accept(); // 创建Socket的连接通道
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ); // 将这个通过注册到Selector

                    System.out.println("新连接channel socket " + socketChannel.socket());
                } else if (key.isReadable()) {
                    // 处理Readable状态Channel

                    SocketChannel socketChannel = (SocketChannel) key.channel();

                    while ((len = socketChannel.read(byteBuffer)) != -1) {
                        if (len > 0) {
                            String msg = new String(byteBuffer.array(), 0, len, StandardCharsets.UTF_8);
                            sb.append(msg);
                            byteBuffer.clear();
                        } else {
                            break;
                        }
                    }
                    System.out.println("接收到消息：" + sb);

                    if (sb.toString().equals("bye")) {
                        System.out.println("断开连接 " + socketChannel.socket());
                        socketChannel.close();
                    }
                    sb.delete(0, sb.length());

                }
                // 本次事件集合中处理完的key要及时删除，不然下次selectionKeys中还会有这个已处理事件的key，最后导致事件重复处理。
                // 注意：只有处理完了，再从集合中删除这个selectionKey下次才不会再次检索到。如果没有处理完，就删除这个selectionKey，下次调用selectionKeys.iterator()
                // 还会检索到这个没处理完的selectionKey
                iterator.remove();
            }
        }
    }
}
