package com.autumn.learn.java8.designpattern.factory;

import java.util.ArrayList;
import java.util.List;

public class NokiaPhone implements Phone {

    String brand = "诺基亚";
    List<String> funcs = new ArrayList<>();
    {
        funcs.add("打电话");
    }

    @Override
    public void listFunction() {
        System.out.println(brand+"手机\t功能:"+funcs);
    }
}
