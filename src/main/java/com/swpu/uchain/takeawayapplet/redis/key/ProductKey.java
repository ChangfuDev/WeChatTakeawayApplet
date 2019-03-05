package com.swpu.uchain.takeawayapplet.redis.key;

/**
 * @Description
 * @Author cby
 * @Date 19-1-20
 **/
public class ProductKey extends BasePrefix {

    public ProductKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static ProductKey productKey = new ProductKey(400, "productName");
}
