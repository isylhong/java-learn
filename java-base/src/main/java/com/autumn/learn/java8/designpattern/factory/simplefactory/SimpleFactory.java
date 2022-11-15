package com.autumn.learn.java8.designpattern.factory.simplefactory;

import com.autumn.learn.java8.designpattern.factory.HuaWeiPhone;
import com.autumn.learn.java8.designpattern.factory.NokiaPhone;
import com.autumn.learn.java8.designpattern.factory.Phone;
import com.autumn.learn.java8.designpattern.factory.XiaoMiPhone;


public class SimpleFactory {

    public Phone productPhone(String brand) {
        if ("NoKia".equals(brand)) {
            return new NokiaPhone();
        } else if ("XiaoMi".equals(brand)) {
            return new XiaoMiPhone();
        } else if ("HuaWei".equals(brand)) {
            return new HuaWeiPhone();
        } else {
            return new NokiaPhone();
        }
    }
}
