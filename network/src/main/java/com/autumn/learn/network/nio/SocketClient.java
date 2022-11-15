package com.autumn.learn.network.nio;

import com.autumn.learn.network.IOUtil;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * socket客户端
 *
 * @author yl
 * @since 2022-11-12 15:17
 */
public class SocketClient {
    public void main(String args[]) throws Exception {
        // 要连接的服务端IP地址和端口
        String host = "127.0.0.1";
//        int port = 50000; // BIO
        int port = 50001; // NIO
//        int port = 50002; // Netty

        // 与服务端建立连接
        Socket socket = new Socket(host, port);
        OutputStream outputStream = socket.getOutputStream(); // 获得输出流

        String msgSend;
        Scanner sc = new Scanner(System.in);
        do {
            msgSend = sc.next();
            outputStream.write(msgSend.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } while (!msgSend.equals("bye"));
        IOUtil.closeStream(outputStream);
        IOUtil.closeSocket(socket);
    }
}
