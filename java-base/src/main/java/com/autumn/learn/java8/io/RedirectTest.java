package com.autumn.learn.java8.io;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 *
 * @author yl
 * @since 2022-11-14 22:32
 */
public class RedirectTest {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        System.out.println("aaa");
        new RedirectTest().testRedirectInputStream();
    }

    /**
     * 输出重定向
     */
    @Test
    public void testRedirectOutputStream() {
        String targetOutPut = "./output.txt";

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(targetOutPut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert fos != null;
        PrintStream ps = new PrintStream(fos); // 重定向标准输出

        System.setOut(ps);
        System.out.println("输出重定向");

        new Thread(() -> System.out.println("1111111")).start(); // 子线程共享父线程中的标准输入|输出
    }

    /**
     * 输入重定向
     */
    @Test
    public void testRedirectInputStream() {
        String targetOutPut = "./input.txt";

        InputStream STDIN = System.in; // 先保存默认的标准输入

        FileInputStream fos = null;
        try {
            fos = new FileInputStream(targetOutPut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.setIn(fos); // 重定向标准输入

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            System.out.println(sc.next());
        }

        System.setIn(STDIN); // 将System.in重新指回默认标准输入
        Scanner sc2 = new Scanner(System.in);
        String next = sc2.next(); //
        System.out.println(next);
    }
}
