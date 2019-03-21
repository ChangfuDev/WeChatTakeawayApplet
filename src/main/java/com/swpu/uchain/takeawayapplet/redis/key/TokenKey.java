package com.swpu.uchain.takeawayapplet.redis.key;

/**
 * @ClassName TokenKey
 * @Author hobo
 * @Date 19-3-21 上午9:58
 * @Description
 **/
public class TokenKey extends BasePrefix {
    public TokenKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static TokenKey tokenKey = new TokenKey(7200, "token_%s");
}
