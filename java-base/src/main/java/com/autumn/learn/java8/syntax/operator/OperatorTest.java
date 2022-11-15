package com.autumn.learn.java8.syntax.operator;

import com.autumn.learn.java8.utils.NumberUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperatorTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperatorTest.class);

    @Test
    public void testGetLowestOneBit() {
        int num = 6;
        int lowest = getLowestOneBit(num);
        LOGGER.info("lowest one bit of '{}' is {}({})", num, lowest, NumberUtil.toBinaryString32(lowest));
    }

    @Test
    public void testGetHighestOneBit() {
        int i = 7;
        int highest = getHighestOneBit(i);
        LOGGER.info("highest one bit of '{}' is {}({})", i, highest, NumberUtil.toBinaryString32(highest));
    }

    /**
     * 给定一个整数X,返回一个整数Y。Y为X对应二进制数最低位1所对应二进制数的十进制数值
     * 如 X=10(1010), 则Y=2(0010)
     * 如 X=12(1100), 则Y=4(0100)
     * 算法：A与-A进行&运算
     */
    public int getLowestOneBit(int i) {
        // HD, Section 2-1
        return i & -i;
    }

    /**
     * 给定一个整数X,返回一个整数Y。Y为X对应二进制数除最高一位有效位外，低位全为0的二进制数Z对应的十进制数值
     * 如 X=5(101), 则Y=4(100)
     * 如 X=2(010), 则Y=2(010)
     * 算法：从A的二进制最高有效位置开始，将后面的所有比特置为1，得到二进制B；二进制B带符号右移一位得到C；B-C即可得到最高位。
     * 5 101
     * 第一步：101 将后面的所有比特置为1得，111
     * 第二步：111 右移1位得，11
     * 第三步：111 - 11 = 100（二进制减法）
     */
    public int getHighestOneBit(int i) {
        i |= (i >> 1);
        i |= (i >> 2);
        i |= (i >> 4);
        i |= (i >> 8);
        i |= (i >> 16);
        return i - (i >>> 1);
    }

    /**
     * (<<)带符号左移，右边补0
     * (>>)带符号右移，正数左边补0，负数左边补1
     * (>>>)无符号右移，正负数左边都补0
     */
    @Test
    public void testBitMove() {

        int x = 0xfffffffe; // 0xfffffffe计算机中存储为补码，对应的十进制值：-2
        System.out.println(x);

        // 负数带符号左移(<<)
        // 1 0000000 00000000 00000000 00000010    -2原码
        // 1 1111111 11111111 11111111 11111101    -2反码
        // 1 1111111 11111111 11111111 11111110    -2补码
        // 1 1111111 11111111 11111111 11111000    <<左移2位，右边填0
        // 1 0000000 00000000 00000000 00001000    ③原码；对应真值：-8
        int i1 = x << 2;
        LOGGER.info("-2 << 2 = {}", i1);

        // 负数无符号右移(>>)
        int i2 = x >> 1;
        LOGGER.info("-2 >> 1 = {}", i2);

        // 负数无符号右移(>>>)
        // 1 0000000 00000000 00000000 00000010    -2原码
        // 1 1111111 11111111 11111111 11111101    -2反码
        // 1 1111111 11111111 11111111 11111110    -2补码
        // 0 0111111 11111111 11111111 11111111    >>无符号右移2位 ③
        // 0 0111111 11111111 11111111 11111111    ③原码，真值：2^30-1 = 1073741823
        int i3 = x >>> 2;
        LOGGER.info("-2 >>> 2 = {}", i3);


        int y = 0x2; // 对应十进制值：2

        // 正数无符号左移(<<)
        int i4 = y << 2;
        LOGGER.info("2 << 2 = {}", i4);

        // 正数无符号右移(>>)
        int i5 = y >> 1;
        LOGGER.info("2 >> 1 = {}", i5);

        // 正数无符号右移(>>>)
        int i6 = y >>> 1;
        System.out.println(NumberUtil.toBinaryString32(i6));
        LOGGER.info("2 >>> 1 = {}", i6);
    }


    // 运算符 与(&), 或(|), 非(~), 异或(^)
    @Test
    public void testYuHuoFei() {
        int x = 0x05; // 0 0000000 00000000 00000000 00000101
        int y = 0x06; // 0 0000000 00000000 00000000 00000110

        // 与
        int i1 = x & y;
        LOGGER.info("{} & {} = {}", x, y, i1);

        // 或
        int i2 = x | y;
        LOGGER.info("{} | {} = {}", x, y, i2);

        // 非(带符号取反)
        // 0 0000000 00000000 00000000 00000101    0x05原码 ①
        // 1 1111111 11111111 11111111 11111010    0x05取反，得到补码形式负数（带符号） ②
        // 1 0000000 00000000 00000000 00000110    求②的原码（负数，计算机存储其补码）
        int i3 = ~x; // -{16-(16-1-5)}=-(16-10)=-6      16-1-5：取5的反码的绝对值
        LOGGER.info("~{} = {}", x, i3);

        // 异或
        int i4 = x ^ y;
        LOGGER.info("{} ^ {} = {}", x, y, i4);
    }
}
