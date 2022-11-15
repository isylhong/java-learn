package com.autumn.learn.java8.designpattern.prototype.deepClone;

public class Client {
    public static void main(String[] args) {
        Car car = new Car();
        Car cloneCar = null;
        try {
            cloneCar = (Car) car.deepClone();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("car == cloneCar?");
        System.out.println(car == cloneCar);

        System.out.println("\ncar.getBrand() == cloneCar.getBrand()?");
        System.out.println(car.getBrand() == cloneCar.getBrand());
    }
}
