package com.autumn.learn.algorithm;

import org.junit.Test;


public class MyTest {

    @Test
    public void test01() {

        int i, j, k;
        for (i = 'x'; i <= 'z'; i++) {
            for (j = 'x'; j <= 'z'; j++) {
                for (k = 'x'; k <= 'z'; k++) {
                    if (i == 'x' || j == i || k == 'x' || k == 'z' || j == k || i == k) {
                        continue;
                    }
                    System.out.println("a:" + (char) i + ",b:" + (char) j + ",c:" + (char) k);
                }
            }
        }
    }

}
