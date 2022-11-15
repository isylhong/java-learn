package com.autumn.learn.network.bio;

import com.autumn.learn.network.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * BIO模式下，工作线程A等待读取Stream中的内容。直接与Stream交互。
 * 工作线程A从SocketA连接完成开始，便一直处于阻塞状态，直到Stream中数据准备好了才会被唤醒处理数据。
 * 工作线程A阻塞等待SocketA中的数据，也就不能去处理其他数据已经准备好但没线程处理的SocketB。
 *
 * @author yl
 * @since 2022-11-12 15:06
 */
public class BioWorkerThread implements Runnable {
    private final Socket socket;
    private final StringBuilder sb = new StringBuilder();

    public BioWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        try {
            is = socket.getInputStream();
            byte[] buff = new byte[1024];
            int len;
            String msg = "";
            while ((len = is.read(buff)) != -1) {
                msg = new String(buff, 0, len, StandardCharsets.UTF_8);
                System.out.println(msg);
                sb.append(msg); // 记录发送的数据
                if (msg.equals("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(sb); // 打印所有发送的数据
            IOUtil.closeStream(is);
            IOUtil.closeSocket(socket);
        }
    }
}
