package com.swpu.uchain.takeawayapplet.dto;

import com.swpu.uchain.takeawayapplet.entity.OrderDetail;
import lombok.Data;
import redis.clients.jedis.BinaryClient;

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

}
