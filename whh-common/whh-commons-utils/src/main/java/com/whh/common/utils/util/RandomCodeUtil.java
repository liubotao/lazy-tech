package com.whh.common.utils.util;

import java.util.Random;

/**
 * 随机数生成器
 */
public class RandomCodeUtil {
    private static String[] FEED_NUMBERS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static String[] FEED_CHARS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e",
            "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
            "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * 生成指定长度的验证码:数字+大小写英文字母
     *
     * @param length
     * @param isNumeric 验证码是否为纯数字
     * @return String
     * @author Q
     */
    public static String createRandomCode(int length, boolean isNumeric) {
        return isNumeric ? createRandomNumbers(length) : createRandomStrings(length);
    }

    public static String createRandomNumbers(int length) {
        StringBuilder strBuilder = new StringBuilder();
        Random random = new Random();
        //生成验证码
        for (int i = 0; i < length; i++) {
            strBuilder.append(FEED_NUMBERS[random.nextInt(FEED_NUMBERS.length)]);
        }
        return strBuilder.toString();
    }

    public static String createRandomStrings(int length) {
        StringBuilder strBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            strBuilder.append(FEED_CHARS[random.nextInt(FEED_CHARS.length)]);
        }
        return strBuilder.toString();
    }

    /**
     * 生成指定长度的验证码:数字
     *
     * @param length
     * @return String
     * @author Q
     */
    public static String createRandomCode(int length) {
        return createRandomCode(length, true);
    }

    /**
     * 主函数，作为调用和测试生成验证码方法的入口
     *
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println("【" + i + "】" + "      【the verification code is】       " + createRandomCode(6));
        }
    }
}
