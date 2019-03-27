package com.swpu.uchain.takeawayapplet.controller;

import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.form.PayForm;
import com.swpu.uchain.takeawayapplet.service.PayService;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName PayController
 * @Author hobo
 * @Date 19-3-24 下午8:23
 * @Description
 **/
@RestController
@RequestMapping("/pay")
@Api(tags = "微信支付接口文档")
public class PayController {

    @Autowired
    private PayService payService;


    @GetMapping(value = "/creat", name = "发起预支付请求")
    public Object creat(PayForm payForm, HttpServletRequest request) {
        return payService.creat(payForm, request);
    }

    @GetMapping(value = "/notify", name = "异步通知接口")
    public Object notify(HttpServletRequest request, HttpServletResponse response) {
        try {
            return payService.notify(request, response);
        } catch (Exception e) {
            return ResultUtil.error(ResultEnum.PAY_FILE);
        }
    }
}
