package com.swpu.uchain.takeawayapplet.controller;

import com.swpu.uchain.takeawayapplet.dto.OrderDTO;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.form.PayForm;
import com.swpu.uchain.takeawayapplet.service.OrderService;
import com.swpu.uchain.takeawayapplet.service.PayService;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@Api(tags = "微信支付接口")
public class PayController {

    @Autowired
    private PayService payService;


    @Autowired
    private OrderService orderService;


    @ApiOperation("发起预支付请求")
    @PostMapping(value = "/creat", name = "发起预支付请求-统一下单")
    public Object creat(PayForm payForm, String code, HttpServletRequest request) {
        OrderDTO orderDTO = orderService.findOrder(payForm.getId());
        if (orderDTO == null) {
            return ResultUtil.error(ResultEnum.ORDER_NOT_FOUND);
        }
        return payService.creat(payForm, code, request);
    }

    @ApiOperation("异步回调")
    @GetMapping(value = "/notify", name = "异步通知接口-完成支付")
    public Object notify(HttpServletRequest request, HttpServletResponse response) {
        try {
            return payService.notify(request, response);
        } catch (Exception e) {
            return ResultUtil.error(ResultEnum.PAY_FILE);
        }
    }

}
