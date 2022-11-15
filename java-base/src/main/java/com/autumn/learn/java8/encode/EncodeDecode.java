package com.autumn.learn.java8.encode;

import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EncodeDecode {
    private static final Charset GBK = Charset.forName("GBK");

    @Test
    public void testDecode() {
        String str = "广东深圳";

        byte[] utf8Bytes = str.getBytes(StandardCharsets.UTF_8);
        System.out.println("utf-8字节数组：" + Arrays.toString(utf8Bytes));

        byte[] gbkBytes = str.getBytes(GBK);
        System.out.println("gbk字节数组：" + Arrays.toString(gbkBytes));


        System.out.println("------锟拷码------"); // GBK编码, UTF-8解码, UTF-8编码, UTF-8解码(锟拷码)
        String gbkUtf8Str = new String(gbkBytes, StandardCharsets.UTF_8); // GBK编码(source) -> UTF-8解码
        System.out.println(gbkUtf8Str); //
        byte[] gbkUtf8Bytes = gbkUtf8Str.getBytes(StandardCharsets.UTF_8); // UTF-8编码
        System.out.println("gbk-utf8-utf8字节数组：" + Arrays.toString(gbkUtf8Bytes)); //
        String gbkUtf8Utf8GbkStr = new String(gbkUtf8Bytes, GBK); // GBK解码
        System.out.println("锟拷码[GBK(source) -> UTF-8 -> UTF-8 -> GBK]：" + gbkUtf8Utf8GbkStr); // 锟姐东锟斤拷锟斤拷


        System.out.println("------回原字符------"); // UTF-8编码，GBK解码，GBK编码，UTF-8解码(原字符)
        String utf8GbkStr = new String(utf8Bytes, GBK); // UTF-8编码(source) -> GBK解码
        System.out.println(utf8GbkStr); //
        byte[] utf8GbkBytes = utf8GbkStr.getBytes(GBK); // GBK编码
        System.out.println("gbk-utf8-utf8字节数组：" + Arrays.toString(utf8GbkBytes)); //
        String utf8GbkGbkUtf8Str = new String(utf8GbkBytes, StandardCharsets.UTF_8); // UTF-8解码
        System.out.println("锟拷码[UTF-8(source) -> GBK -> GBK -> UTF-8)]: " + utf8GbkGbkUtf8Str); // 广东深圳
    }

}
