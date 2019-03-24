package com.swpu.uchain.takeawayapplet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @ClassName WeChatAccountConfig
 * @Author hobo
 * @Date 19-3-8 下午9:49
 * @Description
 **/
@Data
@ConfigurationProperties(prefix = "wechat")
@Component
public class WeChatProperties {


    /**
     * 微信小程序appId
     */
    private String appid;

    /**
     * 微信小程序的Secret
     */
    private String secret;

    /**
     * 微信小程序消息服务器配置的token
     */
    private String token;

    /**
     * 设置微信小程序消息服务器配置的EncodingAESKey
     */
    private String aesKey;

    /**
     * 消息格式，XML或者JSON
     */
    private String msgDataFormat;

    /**
     * 开放平台id
     */
    private String openAppId;

    /***
     * 开放平台秘钥
     */
    private String openAppSecret;
    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String keyPath;

    /**
     * 微信异步通知地址
     */
    private String notifyUrl;

    /**
     * 商品简单描述
     */
    private String title;

    /**
     * 支付类型 小程序为JSAPI
     */
    private String tradeType;

}
