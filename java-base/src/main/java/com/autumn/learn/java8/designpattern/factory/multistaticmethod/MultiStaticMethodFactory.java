package com.autumn.learn.java8.designpattern.factory.multistaticmethod;

import com.autumn.learn.java8.designpattern.factory.HuaWeiPhone;
import com.autumn.learn.java8.designpattern.factory.NokiaPhone;
import com.autumn.learn.java8.designpattern.factory.Phone;
import com.autumn.learn.java8.designpattern.factory.XiaoMiPhone;


public class MultiStaticMethodFactory {

    public static Phone productNokiaPhone() {
        return new NokiaPhone();
    }

    public static Phone productXiaoMiPhone() {
        return new XiaoMiPhone();
    }

    public static Phone productHuaWeiPhone() {
        return new HuaWeiPhone();
    }
}
