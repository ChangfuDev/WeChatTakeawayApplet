package com.swpu.uchain.takeawayapplet.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName TimeUtil
 * @Author hobo
 * @Date 19-3-2 下午7:01
 * @Description 时间格式化工具
 **/
public class TimeUtil {
    /**
     * 获得当前的时间 格式为 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 获得当前的时间 格式为：yyyy年yy月yy日 HH:mm:ss
     *
     * @return
     */
    public static String getTimeCN() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return format.format(date);
    }

}
