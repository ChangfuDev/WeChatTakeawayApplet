package com.swpu.uchain.takeawayapplet.service;


public interface MessageService {

    /**
     * 向单用户发送支付成功模板消息
     *
     * @param openId //用户openID
     * @param formId //预支付成功交易回话标识
     * @param orderNo //订单编号
     * @param orderAmount //订单金额
     * @return void
     * @author hobo
     */
    public void pushToBuyerPaidMsg(String openId, String formId, String orderNo, String orderAmount);

    public String pushToBuyerRefundMsg(String openId, String formId);
}
