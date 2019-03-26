package com.swpu.uchain.takeawayapplet.controller;

import com.swpu.uchain.takeawayapplet.form.LoginForm;
import com.swpu.uchain.takeawayapplet.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginController
 * @Author hobo
 * @Date 19-3-26 下午5:15
 * @Description
 **/
@RestController
@Api(tags = "登录")
@CrossOrigin
public class LoginController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/login", name = "管理员登录")
    public Object login(LoginForm loginForm,
                        HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,Authorization");
        response.addHeader("Access-Control-Expose-Headers", "Origin, X-Requested-With, Content-Type, Accept,Authorization");
        return userService.login(loginForm, response);
    }

}
