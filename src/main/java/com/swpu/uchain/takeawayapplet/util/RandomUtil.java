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
}
