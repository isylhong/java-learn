package com.autumn.learn.java8.designpattern.factory.abstractfactory;

import com.autumn.learn.java8.designpattern.factory.Phone;

import java.util.ArrayList;
import java.util.List;

public class NokiaPlusPhone implements Phone {
    String brand = "诺基亚";
    List<String> funcs = new ArrayList<>();

    {
        funcs.add("打电话");
        funcs.add("听歌");
        funcs.add("看视频");
        funcs.add("玩游戏");
    }

    @Override
    public void listFunction() {
        System.out.println(brand + "手机\t功能:" + funcs);
    }
}
