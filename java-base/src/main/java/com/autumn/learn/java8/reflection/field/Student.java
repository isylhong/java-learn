package com.autumn.learn.java8.reflection.field;

public class Student {
    //**********字段*************//
    public String name;
    protected int age;
    char sex;
    private String phoneNum;

    public Student(){
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}