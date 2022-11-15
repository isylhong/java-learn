package com.autumn.learn.java8.datetime;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author yl
 * @since 2022-11-12 13:40
 */
public class CalendarTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalendarTest.class);

    @Test
    public void testCustomMethod() {
        System.out.println(getCurrentMonthDay());
        String PayrollPeriod = "202211";
        int year = Integer.parseInt(PayrollPeriod.substring(0, 4));
        int month = Integer.parseInt(PayrollPeriod.substring(4, 6));
        System.out.println(getDaysByYearMonth(year, month));
    }

    /**
     * @return 当月天数
     */
    public static int getCurrentMonthDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1); // Calendar.DATE 与 Calendar.DAY_OF_MONTH 含义相同，值一样
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        return cal.get(Calendar.DATE);
    }

    /**
     *
     * @param year 年份
     * @param month 月份
     * @return 指定年月的月天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        return cal.get(Calendar.DATE);
    }

    /**
     * Calendar.set()
     */
    @Test
    public void testMethod_Set() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 8 - 1); // 八月
        cal.set(Calendar.DAY_OF_MONTH, 31); // 31号，八月最后一天
        System.out.println(sdf.format(cal.getTime()));

        cal.set(Calendar.MONTH, Calendar.SEPTEMBER); // 设置月份为九月
        System.out.println(sdf.format(cal.getTime())); // 2022-10-01。八月有31天，但九月只有30天，31>30, 导致月份进位
    }


    /**
     * Calendar.add()方法
     * 低时间单位值向上溢出：向高时间字段进位
     * 低时间单位值向下溢出：向高时间字段借位
     */
    @Test
    public void testMethod_Add() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 8 - 1); // 八月
        cal.set(Calendar.DAY_OF_MONTH, 31); // 31号，八月最后一天
        System.out.println(sdf.format(cal.getTime())); // 2022-08-31
        cal.add(Calendar.DAY_OF_MONTH, 1); // 天数+1
        System.out.println(sdf.format(cal.getTime())); // 2022-09-01。八月只有31天，天数向上溢出导致月份进位。


        cal.set(Calendar.MONTH, Calendar.MARCH); // 三月
        cal.set(Calendar.DAY_OF_MONTH, 1); // 1号，三月第一天
        System.out.println(sdf.format(cal.getTime())); // 2022-03-01
        cal.add(Calendar.DAY_OF_MONTH, -1);
        System.out.println(sdf.format(cal.getTime())); // 2022-02-28。月天数最低值为1，天数向下溢出导致月份借位
    }

    /**
     * Calendar.roll()方法
     * 低时间单位值溢出：不会向高时间字段进位或借位
     */
    @Test
    public void testMethod_Roll() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 8 - 1); // 八月
        cal.set(Calendar.DAY_OF_MONTH, 31); // 31号，八月最后一天
        System.out.println(sdf.format(cal.getTime()));
        cal.roll(Calendar.DAY_OF_MONTH, 1); // 回滚到当前月起始第一天
        System.out.println(sdf.format(cal.getTime())); // 2022-08-01


        cal.set(Calendar.MONTH, Calendar.MARCH); // 三月
        cal.set(Calendar.DAY_OF_MONTH, 1); // 1号
        System.out.println(sdf.format(cal.getTime()));
        cal.roll(Calendar.DAY_OF_MONTH, -1); // 回滚到当前月结尾最后一天
        System.out.println(sdf.format(cal.getTime())); // 2022-03-31
    }

    @Test
    public void testCalendarField() {
        Calendar cal = Calendar.getInstance();

        LOGGER.info("YEAR: {}", cal.get(Calendar.YEAR));
        LOGGER.info("MONTH: {}", cal.get(Calendar.MONTH));
        LOGGER.info("DAY OF MONTH: {}", cal.get(Calendar.DAY_OF_MONTH));
        LOGGER.info("DAY OF WEEK: {}", cal.get(Calendar.DAY_OF_WEEK));
        LOGGER.info("HOUR OF DAY: {}", cal.get(Calendar.HOUR_OF_DAY));
        LOGGER.info("MINUTE: {}", cal.get(Calendar.MINUTE));
        LOGGER.info("MILLISECOND: {}", cal.get(Calendar.MILLISECOND));
    }
}
