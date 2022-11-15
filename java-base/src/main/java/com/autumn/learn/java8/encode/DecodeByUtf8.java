package com.autumn.learn.java8.encode;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 编码：将字符转换为字节数组
 * 解码：将字节数组转换为字符
 *
 * @author yl
 * @since 2022-11-12 13:50
 */
public class DecodeByUtf8 {
    public static final Charset GBK = Charset.forName("GBK");

    public static void main(String[] args) {
        String str = "广东深圳";
        byte[] gbkBytes = str.getBytes(GBK); // 1.GBK编码
        System.out.println("source gbk bytes: " + Arrays.toString(gbkBytes));


        System.out.println("\n------口字码------"); // GBK编码 -> UTF-8解码 （古文码）
        String gbkUtf8Str = new String(gbkBytes, StandardCharsets.UTF_8); // 2.UTF-8解码
        System.out.println("[GBK(source) -> UTF-8]: " + gbkUtf8Str); // �㶫����


        System.out.println("\n------问号码------"); // GBK编码(source) -> UTF-8解码 -> GBK编码 -> UTF-8解码 （问号码）
        byte[] gbkUtf8GbkBytes = gbkUtf8Str.getBytes(GBK); // 3.GBK编码
        System.out.println("gbk-utf8-gbk byte array: " + Arrays.toString(gbkUtf8GbkBytes));
        String gbkUtf8GbkUtf8Str = new String(gbkUtf8GbkBytes, StandardCharsets.UTF_8); // 4.UTF-8解码
        System.out.println("[GBK(source) -> UTF-8 -> GBK -> UTF-8]: " + gbkUtf8GbkUtf8Str);  // ??????


        System.out.println("\n------回原字符------"); // UTF-8编码，GBK解码，GBK编码，UTF-8解码(原字符)
        byte[] utf8Bytes = str.getBytes(StandardCharsets.UTF_8); // UTF-8编码(source)
        String utf8GbkStr = new String(utf8Bytes, GBK); // GBK解码
//        System.out.println(utf8GbkStr); //
        byte[] utf8GbkGbkBytes = utf8GbkStr.getBytes(GBK); // GBK编码
        System.out.println("utf8-gbk-gbk byte array: " + Arrays.toString(utf8GbkGbkBytes));
        String utf8GbkGbkUtf8Str = new String(utf8GbkGbkBytes, StandardCharsets.UTF_8); // UTF-8解码
        System.out.println("[UTF-8(source) -> GBK -> GBK -> UTF-8)]: " + utf8GbkGbkUtf8Str); // 广东深圳
    }
}
