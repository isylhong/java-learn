package com.autumn.learn.java8.designpattern.factory;

import java.util.ArrayList;
import java.util.List;

public class HuaWeiPhone implements Phone {

    String brand = "华为";
    List<String> funcs = new ArrayList<>();
    {
        funcs.add("打电话");
        funcs.add("听歌");
        funcs.add("看视频");
    }

    @Override
    public void listFunction() {
        System.out.println(brand+"手机\t功能:"+funcs);
    }
}
