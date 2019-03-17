package com.swpu.uchain.takeawayapplet.util;

import com.swpu.uchain.takeawayapplet.enums.CodeEnum;

/**
 * @ClassName EnumUtil
 * @Author hobo
 * @Date 19-3-17 下午3:22
 * @Description
 **/
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
