package com.autumn.learn.java8.designpattern.prototype;

public class Client {
    public static void main(String[] args) throws CloneNotSupportedException {
        Car car, cloneCar;
        car = new Car();
        cloneCar = (Car) car.clone();

        System.out.println("car == cloneCar?");
        System.out.println(car == cloneCar);

        System.out.println("\ncar.getBrand() == cloneCar.getBrand()?");
        System.out.println(car.getBrand() == cloneCar.getBrand());
    }
}
