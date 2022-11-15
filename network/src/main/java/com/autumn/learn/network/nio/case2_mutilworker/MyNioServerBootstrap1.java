package com.autumn.learn.network.nio.case2_mutilworker;

import com.autumn.learn.network.nio.MyWorkerGroup;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Reactor多线程模型：三种角色(Reactor、Acceptor、Worker)
 * 1）由一个专门的Acceptor线程监听服务端指定端口，接收客户端的TCP连接请求；
 * 2）网络IO操作-读、写等由一个NIO线程池负责，线程池可以采用标准的JDK线程池实现，它包含一个任务队列和N个可用的NIO线程，由这些NIO线程负责消息的读取、解码、编码和发送；
 * 3）1个Worker线程可以同时处理N条链路，但是1个链路只对应1个Worker线程，防止发生并发操作问题。
 *
 *
 * Worker多线程注意事项：不能简单的在主线程(Acceptor线程)中使用Worker线程池中的线程去处理已经就绪的Channel中的数据。
 * 原因：假设线程池中的线程A正在处理分配给它的channelA中的数据且并未处理完，此时主线程中循环调用的Selector.select()会重复查询到未处理完的channelA对应的key，
 * 然后又将channelA分配给线程B去处理，导致channelA的数据重复处理。
 *
 * 多worker线程应该为每个worker线程创建一个独立的Selector,并将分配给worker线程处理的通道注册到属于这个worker线程的Selector中。
 *
 * @author yl
 * @since 2022-11-12 15:07
 */
public class MyNioServerBootstrap1 {

    public void main(String[] args) throws IOException {
        int nThread = 1;
        new MyNioServerBootstrap1().start(nThread);
    }

    public void start(int nThread) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false); // 设置通道为非阻塞

        ServerSocket serverSocket = serverSocketChannel.socket(); // 通过通道ServerSocketChannel获取一个ServerSocket
        serverSocket.bind(new InetSocketAddress(50001)); // ServerSocket绑定监听50001端口

        Selector selector = Selector.open(); // 创建一个Selector
        // 将ServerSocketChannel注册到Selector中，并设置监听Socket连接准备好事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        MyWorkerGroup workerGroup = new MyWorkerGroup(nThread);
        MyReactorAndAcceptor myReactorAndAcceptor = new MyReactorAndAcceptor(workerGroup, selector);
        myReactorAndAcceptor.start();

        System.out.println("服务启动成功!");
    }
}
