package com.autumn.learn.java8.designpattern.prototype.deepClone;

import java.io.*;

public class Car implements Serializable {
    private static final long serialVersionUID = 1L;
    private Brand brand;

    public Car() {
        this.brand = new Brand();
    }

    public Object deepClone() throws IOException, ClassNotFoundException {
        //将对象写入流中
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(this);

        //将对象从流中取出
        ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (ois.readObject());
    }

    public Brand getBrand() {
        return this.brand;
    }

    public void run() {
        System.out.println("小汽车能跑了");
    }
}

