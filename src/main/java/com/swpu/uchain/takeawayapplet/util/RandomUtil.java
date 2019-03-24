package com.swpu.uchain.takeawayapplet.util;

import java.util.Random;

/**
 * @ClassName RandomUtil
 * @Author hobo
 * @Date 19-3-4 下午12:52
 * @Description
 **/
public class RandomUtil {
    /**
     * @return java.lang.String
     * @Author hobo
     * @Description : 生成唯一的主键
     * 格式 ：时间+随机数
     **/
    public static synchronized Long creatRandom() {
        Random random = new Random();
        int number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + number;
    }

    /**
     * @return java.lang.String
     * @Author hobo
     * @Description : 获取一定长度的字符串，范围0~9，a-z
     * @Param [length] 指定字符串长度
     **/
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(base.length());
            sb.append(base.charAt(num));
        }
        return sb.toString();
    }

    /**
     * @Author hobo
     * @Description : 生成6~10位或10位随机数
     * @Param [codeLength]
     * @return java.lang.String
     **/
    public static String creatCode(int codeLength) {
        String code = "";
        for (int i = 0; i < codeLength; i++) {
            code += (int) (Math.random() * 9);
        }
        return code;
    }
}
