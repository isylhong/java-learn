package com.autumn.learn.java8.encode;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 编码：将字符转换为字节数组
 * 解码：将字节数组转换为字符
 *
 * @author yl
 * @since 2022-11-12 13:48
 */
public class DecodeByGbk {
    public static final Charset GBK = Charset.forName("GBK");

    public static void main(String[] args) {

        String str = "广东深圳";
        byte[] utf8Bytes = str.getBytes(StandardCharsets.UTF_8); // 1.UTF-8编码
        System.out.println("utf-8 bytes: " + Arrays.toString(utf8Bytes));


        System.out.println("\n------古文码------"); // UTF-8编码，GBK解码 （古文码）
        String utf8GbkStr = new String(utf8Bytes, GBK); // 2.GBK解码
        System.out.println("[UTF-8(source) -> GBK]: " + utf8GbkStr); // 骞夸笢娣卞湷


        System.out.println("\n------其他乱码------"); // UTF-8编码(source) -> GBK解码 -> UTF-8编码 -> GBK解码 （锟拷码）
        byte[] utf8GbkUtf8Bytes = utf8GbkStr.getBytes(StandardCharsets.UTF_8); // 3.UTF-8编码
        System.out.println("utf8-gbk-utf8 byte array: " + Arrays.toString(utf8GbkUtf8Bytes));
        String utf8GbkUtf8GbkUStr = new String(utf8GbkUtf8Bytes, GBK); // 4.GBK解码
        System.out.println("[UTF-8(source) -> GBK -> UTF-8 -> GBK]: " + utf8GbkUtf8GbkUStr);  // 楠炲じ绗㈠ǎ鍗炴狗


        System.out.println("\n------锟拷码------"); // GBK编码, UTF-8解码, UTF-8编码, GBK解码(锟拷码)
        byte[] gbkBytes = str.getBytes(GBK); // GBK编码(source)
        String gbkUtf8Str = new String(gbkBytes, StandardCharsets.UTF_8); // UTF-8解码
//        System.out.println(gbkUtf8Str);
        byte[] gbkUtf8Utf8Bytes = gbkUtf8Str.getBytes(StandardCharsets.UTF_8); // UTF-8编码
        System.out.println("gbk-utf8-utf8 byte array: " + Arrays.toString(gbkUtf8Utf8Bytes));
        String gbkUtf8Utf8GbkStr = new String(gbkUtf8Utf8Bytes, GBK); // GBK解码
        System.out.println("[GBK(source) -> UTF-8 -> UTF-8 -> GBK]: " + gbkUtf8Utf8GbkStr); // 锟姐东锟斤拷锟斤拷
    }
}

