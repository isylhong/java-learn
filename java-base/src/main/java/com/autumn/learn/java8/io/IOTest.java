package com.autumn.learn.java8.io;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author yl
 * @since 2022-11-14 22:31
 */
public class IOTest {

    /**
     * 使用 new File("xxx")获取文件系统中的资源
     */
    @Test
    public void testFileSystemResource() {
        try {
            /*
            File参数：
            parent(String): 文件(或目录)的父路径，可以是绝对路径，也可以是相对于项目根路径的相对路径
            child(String): 文件(或目录)相对于第一个参数parent的相对路径
             */
            String fileName = "out/input.txt";
            File file = new File("src/test/java/com/autumn/learn/java8/io", fileName);
            FileInputStream fis = new FileInputStream(file.getCanonicalPath());
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fis.read(bytes)) != -1) {
                System.out.println(new String(bytes, StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 三种方法获取ClassPath路径中的资源：
     * 1. XXXClass.getResource("fileName")
     *      fileName不以/开头，获取相对于XXXClass路径下的资源。
     * 2. XXXClass.getResource("/fileName")
     *      fileName以/开头，获取相对于所有java编译文件*.class根路径下的资源。
     * 3. ClassLoader.getResource("fileName")
     *      fileName以/开头，获取相对于*.class根路径下的资源。
     */
    @Test
    public void testClassPathResource() {
        System.out.println(IOTest.class.getResource("")); // 以TempTest.java编译后的TempTest.class文件(注意，不是.java文件)
        System.out.println(IOTest.class.getResource("/")); // 以所有class文件根路径所在目录的相对路径获取Resource

        System.out.println();
        ClassLoader classLoader = IOTest.class.getClassLoader();
        System.out.println(classLoader.getResource(""));  // 以class文件根路径所在目录的相对路径获取Resource，同Class.getResource("/"))
        System.out.println(classLoader.getResource("/")); // null
    }
}
