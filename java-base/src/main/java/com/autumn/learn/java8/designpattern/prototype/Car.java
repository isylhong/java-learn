package com.autumn.learn.java8.designpattern.prototype;

public class Car implements Cloneable {
    private Brand brand;

    public Car() {
        this.brand = new Brand();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException{
        Car carClone = null;
        try{
            carClone = (Car) super.clone();
        }catch (CloneNotSupportedException e){
            System.out.println("克隆失败...");
        }
        return carClone;
    }

    public Brand getBrand(){
        return this.brand;
    }

    public void run(){
        System.out.println("小汽车能跑了");
    }
}
