package com.autumn.learn.java8.designpattern.factory.multimethod;

import com.autumn.learn.java8.designpattern.factory.HuaWeiPhone;
import com.autumn.learn.java8.designpattern.factory.NokiaPhone;
import com.autumn.learn.java8.designpattern.factory.Phone;
import com.autumn.learn.java8.designpattern.factory.XiaoMiPhone;


public class MultiMethodFactory {

    public Phone productNokiaPhone() {
        return new NokiaPhone();
    }

    public Phone productXiaoMiPhone() {
        return new XiaoMiPhone();
    }

    public Phone productHuaWeiPhone() {
        return new HuaWeiPhone();
    }
}
