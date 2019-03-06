package com.swpu.uchain.takeawayapplet.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    CATEGORY_EXIST(1,"此类目已存在"),
    CATEGORY_NOT_EXIST(2,"类目不存在"),
    PRODUCT_EXIST(3,"该商品已存在" ),
    PRODUCT_NOT_EXIST(4,"此商品不存在"),
    ORDER_NOT_FOUND(5,"订单不存在"),
    ORDER_STATUS_ERROR(6,"订单状态错误"),
    ORDER_CANCEL_FILED(7,"订单取消失败" ),
    PAY_STATUS_ERROR(8, "订单支付状态不正确"),
    NOT_FOUND(404, "url错误,请求路径未找到"),
    REQUEST_METHOD_ERROR(550, "不支持%s的请求方式"),
    SERVER_ERROR(500, "服务器未知错误:%s"),
    BIND_ERROR(511, "参数校验错误:%s"),

   ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
