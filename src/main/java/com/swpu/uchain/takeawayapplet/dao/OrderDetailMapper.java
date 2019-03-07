package com.swpu.uchain.takeawayapplet.dao;

import com.swpu.uchain.takeawayapplet.entity.OrderDetail;
import java.util.List;

public interface OrderDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderDetail record);

    OrderDetail selectByPrimaryKey(Long id);

    List<OrderDetail> selectByOrderId(Long orderId);

    List<OrderDetail> selectAll();

    int updateByPrimaryKey(OrderDetail record);
}