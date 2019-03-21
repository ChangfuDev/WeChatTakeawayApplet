package com.swpu.uchain.takeawayapplet.service;

import com.swpu.uchain.takeawayapplet.entity.User;

public interface UserService {

    User findByOpenId(String openId);

}
