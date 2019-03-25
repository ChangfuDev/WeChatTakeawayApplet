package com.swpu.uchain.takeawayapplet.service;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import com.swpu.uchain.takeawayapplet.entity.User;
import com.swpu.uchain.takeawayapplet.form.LoginForm;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    User getCurrentUser();

    User findUserByUserName(String username);

    ResultVO login(LoginForm loginForm, HttpServletResponse response);
}
