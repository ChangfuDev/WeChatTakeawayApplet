package com.swpu.uchain.takeawayapplet.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swpu.uchain.takeawayapplet.dto.OrderDTO;
import com.swpu.uchain.takeawayapplet.entity.OrderDetail;
import com.swpu.uchain.takeawayapplet.form.OrderForm;

import java.util.List;

/**
 * @ClassName OrderFormConversionDTOUtil
 * @Author hobo
 * @Date 19-3-7 下午1:03
 * @Description
 **/
public class OrderFormConversionDTOUtil {

    public static OrderDTO convert(OrderForm orderForm) {

        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setUserName(orderForm.getName());
        orderDTO.setUserPhone(orderForm.getPhone());
        orderDTO.setUserAddress(orderForm.getAddress());
        orderDTO.setOpenId(orderForm.getOpenId());

        List<OrderDetail> orderDetailList =
                gson.fromJson(orderForm.getItems(),
                        new TypeToken<List<OrderDetail>>() {
                        }.getType());

        orderDTO.setOrderDetails(orderDetailList);

        return orderDTO;
    }
}
