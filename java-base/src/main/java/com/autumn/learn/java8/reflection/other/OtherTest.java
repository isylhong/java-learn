package com.autumn.learn.java8.reflection.other;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Properties;

public class OtherTest {
    public static void main(String[] args) throws Exception {
        // 1.通过反射获取Class对象
        Class stuClass = Class.forName(getValue("className")); // "fanshe.other.Student"
        // 2.获取show()方法
        Method m = stuClass.getMethod(getValue("methodName")); // show
        // 3.调用show()方法
        m.invoke(stuClass.getConstructor().newInstance());

    }

    // 此方法接收一个key，在配置文件中获取相应的value
    public static String getValue(String key) throws Exception {
        String filePath;
        filePath = OtherTest.class.getResource("pro.properties").getPath();

//        filePath = "E:\\SourceCode\\Idea\\Verify\\src\\fanshe\\other\\pro.properties";

//        String root = System.getProperty("user.dir");
//        String fileSeparator = File.separator;
//        System.out.println(root +" "+ fileSeparator);
//        filePath = root + fileSeparator + "src" + fileSeparator + "fanshe" + File.separator + "other" + fileSeparator + "pro.properties";

        System.out.println(filePath);

        Properties pro = new Properties(); // 获取配置文件的对象
        FileReader in = new FileReader(filePath); // 获取输入流
        pro.load(in); // 将流加载到配置文件对象中
        in.close();
        return pro.getProperty(key); // 返回根据key获取的value值
    }
}
