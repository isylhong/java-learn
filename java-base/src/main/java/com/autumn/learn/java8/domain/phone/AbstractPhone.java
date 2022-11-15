package com.autumn.learn.java8.domain.phone;

import com.autumn.learn.java8.clazz.annotation.CustomDescription;

@CustomDescription("抽象手机类AbstractPhone")
public abstract class AbstractPhone implements Phone {
    private String id;
    private String name;

    public AbstractPhone() {
    }

    public AbstractPhone(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return "12cm*6cm";
    }

    public abstract void func();
}
