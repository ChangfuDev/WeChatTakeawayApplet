package com.swpu.uchain.takeawayapplet.service;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import com.swpu.uchain.takeawayapplet.dto.OrderDTO;
import com.swpu.uchain.takeawayapplet.entity.OrderDetail;
import com.swpu.uchain.takeawayapplet.entity.OrderMaster;

import java.util.List;


public interface OrderService {

    boolean insert(OrderDetail orderDetail);

    boolean delete(Long id);

    boolean update(OrderMaster orderMaster);

    List<OrderDetail> selectDetailByOrderId(Long orderId);

    ResultVO findOrderByOrderId(Long orderId);

    ResultVO findListByOpenId(String openId);

    ResultVO creatOrder(OrderDTO orderDTO);

    ResultVO cancelOrder(OrderDTO orderDTO);

    ResultVO finishOrder(OrderDTO orderDTO);

    ResultVO paidOrder(OrderDTO orderDTO);
}
