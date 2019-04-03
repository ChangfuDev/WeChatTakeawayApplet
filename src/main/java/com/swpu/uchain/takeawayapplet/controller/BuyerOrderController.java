package com.swpu.uchain.takeawayapplet.controller;

import com.swpu.uchain.takeawayapplet.dto.OrderDTO;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.form.OrderForm;
import com.swpu.uchain.takeawayapplet.service.OrderService;
import com.swpu.uchain.takeawayapplet.util.GetOpenIdUtil;
import com.swpu.uchain.takeawayapplet.util.OrderFormConversionDTOUtil;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName OrderController
 * @Author hobo
 * @Date 19-3-7 上午11:25
 * @Description
 **/
@RestController
@RequestMapping("/buyer/order")
@Api(tags = "买家订单接口")
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("创建订单接口")
    @PostMapping(value = "/creat", name = "创建订单")
    public Object creatOrder(String code, OrderForm orderForm) {
        orderForm.setOpenId(GetOpenIdUtil.getOpenId(code));

        OrderDTO orderDTO = OrderFormConversionDTOUtil.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetails())) {
            return ResultUtil.error(ResultEnum.ITEMS_EMPTY);
        }
        return orderService.creatOrder(orderDTO);
    }


    @ApiOperation("获取用户的订单信息")
    @GetMapping(value = "/getList", name = "获取用户的订单信息")
    public Object getOrderListByOpenId(String code) {
        return orderService.findListByOpenId(code);
    }


    @ApiOperation("获取订单详情")
    @GetMapping(value = "/getDetail", name = "获取订单详情信息")
    public Object getOrderDetail(Long orderId) {
        OrderDTO orderDTO = orderService.findOrder(orderId);

        return ResultUtil.success(orderDTO);
    }

    @ApiOperation("取消订单")
    @PostMapping(value = "/cancel", name = "取消订单")
    public Object cancel(Long orderId) {

        OrderDTO orderDTO = orderService.findOrder(orderId);
        return orderService.cancelOrder(orderDTO);
    }

}
