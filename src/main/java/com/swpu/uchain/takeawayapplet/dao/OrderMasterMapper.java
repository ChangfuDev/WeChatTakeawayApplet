package com.swpu.uchain.takeawayapplet.dao;

import com.swpu.uchain.takeawayapplet.entity.OrderMaster;

import java.util.List;

public interface OrderMasterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderMaster record);

    OrderMaster selectByPrimaryKey(Long id);

    List<OrderMaster> selectAll();

    List<OrderMaster> findListByOpenId(String openId);

    int updateByPrimaryKey(OrderMaster record);
}