package com.autumn.learn.network;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author yl
 * @since 2022-11-12 15:16
 */
public class IOUtil {
    public static void closeStream(Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void closeSocket(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
