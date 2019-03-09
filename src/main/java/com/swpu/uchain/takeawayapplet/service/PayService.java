package com.swpu.uchain.takeawayapplet.service;


import com.swpu.uchain.takeawayapplet.dto.OrderDTO;

public interface PayService {

    void create(OrderDTO orderDTO);
}
