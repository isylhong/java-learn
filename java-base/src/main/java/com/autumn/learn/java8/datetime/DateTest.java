package com.autumn.learn.java8.datetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {

    public static void main(String[] args) throws ParseException {

        DateTest dateTest = new DateTest();
        System.out.println(dateTest.toString());

        // 完整时间戳的长度为13位
        long timestamp = 123456789L;
        Date date = new Date();
        date.setTime(timestamp);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dateFormat.format(date));

        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2021-11-9");
        System.out.println(date1.getTime());
    }
}
