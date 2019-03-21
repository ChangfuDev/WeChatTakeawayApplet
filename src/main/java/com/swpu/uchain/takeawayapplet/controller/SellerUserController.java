package com.swpu.uchain.takeawayapplet.controller;

import com.swpu.uchain.takeawayapplet.entity.User;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.form.LoginForm;
import com.swpu.uchain.takeawayapplet.redis.RedisService;
import com.swpu.uchain.takeawayapplet.redis.key.TokenKey;
import com.swpu.uchain.takeawayapplet.service.UserService;
import com.swpu.uchain.takeawayapplet.util.CookieUtil;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @ClassName SellerUserController
 * @Author hobo
 * @Date 19-3-21 上午9:23
 * @Description
 **/

@RestController
@RequestMapping("/seller")
@Api(tags = "卖家登录登出接口")
@CrossOrigin
public class SellerUserController {


    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/login")
    public Object login(HttpServletResponse response,
                        LoginForm loginForm) {

        // 1.openId匹配数据库
        User user = userService.findByOpenId(loginForm.getOpenId());
        if (user == null) {
            return ResultUtil.error(ResultEnum.LOGIN_FAIL);
        }

        // 2.设置token至redis
        String token = UUID.randomUUID().toString();
        redisService.set(TokenKey.tokenKey, token, loginForm.getOpenId());

        // 3.设置token至cookie
        CookieUtil.set(response, "token", token, 7200);

        //前端跳转至微信扫码
        return ResultUtil.success();
    }


    @GetMapping("/logout")
    public Object logout(HttpServletRequest request,
                         HttpServletResponse response) {
        Cookie cookie = CookieUtil.get(request, "token");

        redisService.delete(TokenKey.tokenKey, cookie.getValue());

        CookieUtil.set(response, "token", null, 0);
        //注销成功
        return ResultUtil.success();
    }


}
