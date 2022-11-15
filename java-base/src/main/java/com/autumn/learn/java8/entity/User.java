package com.autumn.learn.java8.entity;

public class User {
    private String name;
    private int age;
    Friend friend =  new Friend();

    public User() {
        System.out.println("do User constructor...");
        friend.setName("小明");
    }

    public User(String name,int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    @Override
    public String toString() {
        return "User{" +
                "friend=" + friend +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}
