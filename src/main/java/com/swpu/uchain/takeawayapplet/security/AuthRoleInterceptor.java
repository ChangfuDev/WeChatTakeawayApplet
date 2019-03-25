package com.swpu.uchain.takeawayapplet.security;

import com.alibaba.fastjson.JSON;
import com.swpu.uchain.takeawayapplet.accessctro.RoleContro;
import com.swpu.uchain.takeawayapplet.entity.User;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.service.UserService;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName AuthRoleInterceptor
 * @Description 权限控制
 * @Author hobo
 * @Date 18-12-9 下午3:41
 **/
@Slf4j
@Service
public class AuthRoleInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html ; charset=utf-8");
        String json = JSON.toJSONString(ResultUtil.error(ResultEnum.AUTHENTICATION_ERROR));
        User user = userService.getCurrentUser();
        if (user == null) {
            return true;
        }
        log.info("-------------执行权限验证----------");
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RoleContro roleContro = handlerMethod.getMethodAnnotation(RoleContro.class);
            if (roleContro == null) {
                return true;
            }
            Integer roleValue = roleContro.role().getValue();
            Integer userValue = user.getRole();
            log.info("RoleValue:{},userRole:{}", roleContro, userValue);
            if (userValue >= roleValue) {
                return true;
            }else {
                json = JSON.toJSONString(ResultUtil.error(ResultEnum.PERMISSION_DENNY));
                log.info("-------权限不足--------");
            }
        }
        response.getWriter().append(json);
        return false;
    }
}
