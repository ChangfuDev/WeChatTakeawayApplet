package com.swpu.uchain.takeawayapplet.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    CATEGORY_EXIST(1, "此类目已存在"),
    CATEGORY_NOT_EXIST(2, "类目不存在"),
    PRODUCT_EXIST(3, "该商品已存在"),
    PRODUCT_NOT_EXIST(4, "此商品不存在"),
    ORDER_NOT_FOUND(5, "订单不存在"),
    ORDER_STATUS_ERROR(6, "订单状态错误"),
    ORDER_CANCEL_FILED(7, "订单取消失败"),
    PAY_STATUS_ERROR(8, "订单支付状态不正确"),
    ITEMS_EMPTY(9, "购物车为空"),
    PRODUCT_OF_CATEGORY_NOT_NULL(10, "此类目下商品不为空"),

    WECHAT_MP_ERROR(20, "微信公众账号错误"),
    CODE_EMPTY(21, "code信息为空"),
    NOT_FOUND(404, "url错误,请求路径未找到"),
    REQUEST_METHOD_ERROR(550, "不支持%s的请求方式"),
    SERVER_ERROR(500, "服务器未知错误:%s"),
    BIND_ERROR(511, "参数校验错误:%s"),
    APPID_NOT_EXIST(20, "appid配置错误"),
    USER_CHECK_FAILED(21, "用户信息校验失败"),
    DECRYPTION_FAILURE(22, "用户信息解密失败"),
    ;


    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
