package com.swpu.uchain.takeawayapplet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swpu.uchain.takeawayapplet.entity.OrderDetail;
import com.swpu.uchain.takeawayapplet.enums.OrderStatusEnum;
import com.swpu.uchain.takeawayapplet.enums.PayStatusEnum;
import com.swpu.uchain.takeawayapplet.util.EnumUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName OrderDTO
 * @Author hobo
 * @Date 19-3-4 下午12:22
 * @Description
 **/
@Data
public class OrderDTO {

    private Long id;

    private String openId;

    private String userName;

    private String userPhone;

    private String userAddress;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    private Integer payStatus;

    private String creatTime;

    private String updateTime;

    private List<OrderDetail> orderDetails;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }

}
