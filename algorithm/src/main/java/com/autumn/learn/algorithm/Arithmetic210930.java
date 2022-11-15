package com.autumn.learn.algorithm;

import org.junit.Test;

import java.util.Stack;

public class Arithmetic210930 {

    @Test
    public void test01() {
        String str = "2345";
        result01("", str);
    }

    @Test
    public void test02() {
        System.out.println(result02());
    }

    @Test
    public void test03() {
        String str = "([])()";
        System.out.println(result03(str));
    }

    @Test
    public void test04() {
        String str = "abcabccbaccd";
        System.out.println(getLongestLoopString(str));
    }


    /**
     * 递归求排列组合Ann，并排除特性序列
     * 如：求A33，且排除的序列特征为：3和5不可连接，4不能处于第三位
     * 返回 123，132，213，231，312，321
     *
     * @param str1 序列已组合元素
     * @param str2 剩余未组合元素
     */

    public void result01(String str1, String str2) {
        if (str2.length() == 1) {
            String str = str1 + str2;
            // 排除条件（3和5不可连接，4不能处于第三位）
            if ((str.contains("35") || str.contains("53") || str.indexOf("4") == 2)) {
                return;
            }
            System.out.println(str1 + str2);
            return;
        }
        String str3, str4;
        for (int i = 0; i < str2.length(); i++) {
            str3 = str1 + str2.charAt(i);
            str4 = str2.substring(0, i);
            if (i != str2.length() - 1) {
                str4 += str2.substring(i + 1);
            }
            result01(str3, str4);
        }

    }


    /**
     * 一个整数num+100开平方为整数，再加168后开平方仍为整数；求这个整数num
     *
     * @return
     */
    public int result02() {
        int num = -1;
        double i, j;
        do {
            num++;
            i = Math.sqrt(num + 100);
            j = Math.sqrt(num + 268);
            System.out.println((i - Math.floor(i)) + "-" + (j - Math.floor(j)));
            System.out.println(i - Math.floor(i) == 0.0 && j - Math.floor(j) == 0.0);
        } while (!((i - Math.floor(i)) == 0.0 && (j - Math.floor(j)) == 0.0));
        return num;
    }


    /**
     * 使用栈结构完成括号匹配
     *
     * @param str
     * @return
     */
    public String result03(String str) {
        if (str.length() % 2 == 1) {
            return "N";
        } else {
            Stack<Character> stack = new Stack<Character>();
            for (int i = 0; i < str.length(); i++) {
                if (stack.isEmpty()) {
                    stack.push(str.charAt(i));
                } else if ((stack.peek() == '[' && str.charAt(i) == ']')
                        || (stack.peek() == '(' && str.charAt(i) == ')')) {
                    stack.pop();
                } else {
                    stack.push(str.charAt(i));
                }
            }
            if (stack.isEmpty()) {
                return "Y";
            }
        }
        return "N";
    }


    /**
     * 给定一个字符串，求解最长回环子串
     */
    public String getLongestLoopString(String str) {
        String subStr = "";
        int strLength = str.length();

        if (str.length() == 1) {
            return str;
        }

        int beginIndex;
        int length = 0;

        for (int i = 1; i < strLength; i++) {
            int p1 = 1;
            while (!((i - p1) < 0 || (i + p1) == strLength)) {
                if (str.charAt(i + p1) == str.charAt(i - p1)) {
                    p1++;
                } else {
                    break;
                }
            }
            beginIndex = i - p1 + 1;
            length = 2 * p1 - 1;
            if (length > subStr.length()) {
                subStr = str.substring(beginIndex, beginIndex + length);
            }

            int p2 = 0;
            while (!((i - p2) < 0 || (i + p2) == strLength)) {
                if (str.charAt(i + p2) == str.charAt(i - p2 - 1)) {
                    p2++;
                } else {
                    break;
                }
            }
            beginIndex = i - p2;
            length = 2 * p2;
            if (length > subStr.length()) {
                subStr = str.substring(beginIndex, beginIndex + length);
            }
        }
        return subStr;
    }

    @Test
    public void test05() {
        String str = "abcd";
        System.out.println(compressStringSimple(str));

    }

    /**
     * 利用字符串重复出现的次数，实现一个基本的字符串压缩函数。如：字符串"aabccccddd"压缩后变为"a2b1c4d3 "。
     * 如果压缩后的字符串没有变短，则返回原来的字符串。如：字符串"bbcc"压缩后变为“bbcc”
     */
    public String compressStringSimple(String str) {
        int[] count = new int[128];
        StringBuilder s1 = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!s1.toString().contains("" + c)) {
                s1.append(c);
            }
            byte index = (byte) c;
            count[index] = ++count[index];
        }
        StringBuilder s2 = new StringBuilder();
        for (int i = 0; i < s1.length(); i++) {
            char c2 = s1.charAt(i);
            s2.append(c2).append(count[(byte) c2]);
        }
        System.out.println(s2.toString());

        if (s1.length() == str.length()) {
            return str;
        } else {
            return s2.toString();
        }
    }

    @Test
    public void test06() {
        System.out.println(getNumResult(5, 4));
    }

    /**
     * 求s=a+aa+aaa+aaaa+aa…a的值，其中a是一个数字，个数由n控制。
     */
    public int getNumResult(int num, int count) {
        int sum = 0;
        for (int i = 1; i < count; i++) {
            int temp = 1;
            for (int j = count - i; j > 0; j--)
                temp = temp * 10;
            sum += i * num * temp;
        }
        sum += num * count;
        return sum;
    }

}
