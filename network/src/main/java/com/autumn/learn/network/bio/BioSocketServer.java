package com.autumn.learn.network.bio;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * BIO模式下，工作线程A等待读取Stream中的内容。直接与Stream交互。
 * 工作线程A从SocketA连接完成开始，便一直处于阻塞状态，直到Stream中数据准备好了才会被唤醒处理数据。
 * 工作线程A阻塞等待SocketA中的数据，也就不能去处理其他数据已经准备好但没线程处理的SocketB。
 *
 * @author yl
 * @since 2022-11-12 15:05
 */
public class BioSocketServer {
    public void main(String[] args) throws Exception {
        // 监听指定的端口
        int port = 50000;
        ServerSocket server = new ServerSocket(port);

        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 1L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());

        System.out.println("服务器启动成功！");
        while (true) {
            Socket socket = server.accept(); // 调用accept()后线程将阻塞，直到有客户端连接
            System.out.println("新socket连接: " + socket);
            if (socket != null) {
                executorService.submit(new BioWorkerThread(socket));
            }
        }
    }
}
