package com.swpu.uchain.takeawayapplet.redis.key;

import com.swpu.uchain.takeawayapplet.redis.KeyPrefix;

/**
 * @Description 实现缓存前缀
 * @Author cby
 * @Date 19-1-19
 **/
public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;
    private String prefix;

    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return this.expireSeconds;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }
}
