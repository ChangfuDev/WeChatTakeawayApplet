package com.swpu.uchain.takeawayapplet.redis.key;

/**
 * @ClassName OrderKey
 * @Author hobo
 * @Date 19-3-4 下午12:34
 * @Description
 **/
public class OrderKey extends BasePrefix {
    public OrderKey(String prefix) {
        super(prefix);
    }

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static OrderKey orderKey = new OrderKey(380, "orderId");

    public static OrderKey orderKerByOpenId = new OrderKey(350,"openId");
}
