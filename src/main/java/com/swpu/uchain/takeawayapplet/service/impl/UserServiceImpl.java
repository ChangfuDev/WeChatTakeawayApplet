package com.swpu.uchain.takeawayapplet.service.impl;

import com.swpu.uchain.takeawayapplet.dao.UserMapper;
import com.swpu.uchain.takeawayapplet.entity.User;
import com.swpu.uchain.takeawayapplet.redis.RedisService;
import com.swpu.uchain.takeawayapplet.redis.key.UserKey;
import com.swpu.uchain.takeawayapplet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Author hobo
 * @Date 19-3-20 下午1:43
 * @Description 用户接口实现类
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;


    @Override
    public User findByOpenId(String openId) {
        User user = redisService.get(UserKey.userKey, openId, User.class);
        if (user == null) {
            user = userMapper.selectByopenId(openId);
            if (user != null) {
                redisService.set(UserKey.userKey, openId, user);
            }
        }
        return user;
    }
}
