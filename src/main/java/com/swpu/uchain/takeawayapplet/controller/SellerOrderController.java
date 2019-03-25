package com.swpu.uchain.takeawayapplet.controller;

import com.swpu.uchain.takeawayapplet.accessctro.RoleContro;
import com.swpu.uchain.takeawayapplet.dto.OrderDTO;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.enums.RoleEnum;
import com.swpu.uchain.takeawayapplet.service.OrderService;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SellerOrderController
 * @Author hobo
 * @Date 19-3-17 下午2:46
 * @Description 商家订单接口
 **/

@RoleContro(role = RoleEnum.ADMIN)
@RestController
@RequestMapping("/seller/order")
@Slf4j
@Api(tags = "商家订单接口")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("获取全部订单信息")
    @GetMapping(value = "/list", name = "获取全部订单信息")
    public Object getOrderList() {
        return orderService.findAllList();
    }


    @ApiOperation("商家取消订单")
    @PostMapping(value = "cancel", name = "商家取消订单")
    public Object cancel(Long orderId) {

        OrderDTO orderDTO = orderService.findOrder(orderId);
        if (orderDTO == null) {
            return ResultUtil.error(ResultEnum.ORDER_NOT_FOUND);
        }
        return orderService.cancelOrder(orderDTO);
    }

    @ApiOperation("商家查询订单详情")
    @GetMapping(value = "detail", name = "商家查询订单详情")
    public Object detail(Long orderId) {
        return orderService.findOrderByOrderId(orderId);
    }

    @ApiOperation("商家完成订单")
    @PostMapping(value = "/finish", name = "商家完结订单")
    public Object finish(Long orderId) {
        OrderDTO orderDTO = orderService.findOrder(orderId);
        if (orderDTO == null) {
            return ResultUtil.error(ResultEnum.ORDER_NOT_FOUND);
        }
        return orderService.finishOrder(orderDTO);
    }

}
