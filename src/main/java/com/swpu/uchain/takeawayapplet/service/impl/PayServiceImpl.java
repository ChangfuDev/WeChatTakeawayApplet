package com.swpu.uchain.takeawayapplet.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.swpu.uchain.takeawayapplet.dto.OrderDTO;
import com.swpu.uchain.takeawayapplet.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName PayServiceImpl
 * @Author hobo
 * @Date 19-3-9 下午2:24
 * @Description
 **/
@Slf4j
@Service
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Override
    public void create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getOpenId());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getId() + "");
        payRequest.setOrderName(ORDER_NAME);
        //选择支付方式为微信支付
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】request={}", payRequest);

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】response={}",payResponse);
        //TODO 加入异步通知接口方法

    }
}
