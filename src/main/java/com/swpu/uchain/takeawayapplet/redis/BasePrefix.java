package com.swpu.uchain.takeawayapplet.redis;

/**
 * @Description 实现缓存前缀
 * @Author cby
 * @Date 19-1-19
 **/
public abstract class BasePrefix implements KeyPrefix {

    private int experiSeconds;
    private String prefix;

    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix(int experiSeconds, String prefix) {
        this.experiSeconds = experiSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return this.experiSeconds;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }
}
