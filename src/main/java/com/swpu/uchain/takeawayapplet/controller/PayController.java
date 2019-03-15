package com.swpu.uchain.takeawayapplet.controller;

import com.swpu.uchain.takeawayapplet.dto.OrderDTO;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.exception.GlobalException;
import com.swpu.uchain.takeawayapplet.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName PayController
 * @Author hobo
 * @Date 19-3-9 下午2:18
 * @Description 微信支付接口类
 **/

@Controller
public class PayController {


    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public void create(@RequestParam("orderId") Long orderId,
                       @RequestParam("returnUrl") String returnUrl) {

        //1.查询订单
        OrderDTO orderDto = orderService.findOrder(orderId);
        if (orderDto == null){
            throw new  GlobalException(ResultEnum.ORDER_NOT_FOUND);
        }
        //2.发起支付

    }

}
