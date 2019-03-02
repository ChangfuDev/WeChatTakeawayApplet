package com.swpu.uchain.takeawayapplet.redis;

/**
 * @author cby
 * @date: 19-1-19
 */
public interface KeyPrefix {

    /**
     * 获取过期时间
     *
     * @return
     */
    int expireSeconds();

    /**
     * 获取key前缀
     *
     * @return:
     */
    String getPrefix();
}
