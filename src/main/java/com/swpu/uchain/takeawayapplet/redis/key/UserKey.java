package com.swpu.uchain.takeawayapplet.redis.key;

import com.swpu.uchain.takeawayapplet.entity.User;

/**
 * @ClassName UserKey
 * @Author hobo
 * @Date 19-3-20 下午1:43
 * @Description
 **/
public class UserKey extends BasePrefix {
    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey userKey = new UserKey(400,"openId");
}
