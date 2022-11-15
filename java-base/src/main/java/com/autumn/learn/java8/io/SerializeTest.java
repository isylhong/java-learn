package com.autumn.learn.java8.io;

import lombok.Data;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author yl
 * @since 2022-11-14 22:33
 */
public class SerializeTest {

    @Test
    public void testSerialize() throws IOException {
        MySerial mySerial = new MySerial("202201");
        MySerial.name = "张三";
        mySerial.setAge(20);
        File file = new File("./serial.txt");
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(mySerial);
        oos.close();
    }


    @Test
    public void testDeserialize() throws IOException, ClassNotFoundException {
        MySerial.name = "李四"; // 反序列化前修改静态变量str的值，证明反序列化后类中static型变量str的值为当前JVM中对应static变量的值。实际static修饰的变量不会被序列化
        File file = new File("./serial.txt");
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        ois.close();
        MySerial mySerial = (MySerial) object;
        System.out.println(mySerial);
    }


    /**
     * 序列化与反序列化
     */
    @Test
    public void testSerializeAndDeserialize() throws IOException, ClassNotFoundException {
        // 序列化
        MySerial mySerial = new MySerial("202202");
        MySerial.name = "tony";
        mySerial.setAge(20);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(mySerial);
        oos.close();

        // 反序列化
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object object = ois.readObject();
        System.out.println(object.toString());
        ois.close();
    }

    @Data
    static class MySerial {
        private final String id;
        private static String name;
        private int age;

        public MySerial(String id) {
            this.id = id;
        }
    }
}
