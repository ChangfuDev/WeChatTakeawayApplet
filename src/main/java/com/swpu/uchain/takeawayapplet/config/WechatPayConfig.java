package com.swpu.uchain.takeawayapplet.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @ClassName WechatPayConfig
 * @Author hobo
 * @Date 19-3-9 下午2:32
 * @Description 微信支付配置类
 **/
@Component
public class WechatPayConfig {

    @Autowired
    private WeChatProperties properties;

    @Bean
    public BestPayServiceImpl bestPayService() {

        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return bestPayService;
    }

    @Bean
    public WxPayH5Config wxPayH5Config() {
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(properties.getAppid());
        wxPayH5Config.setAppSecret(properties.getSecret());
        wxPayH5Config.setMchId(properties.getMchId());
        wxPayH5Config.setMchKey(properties.getMchKey());
        wxPayH5Config.setKeyPath(properties.getKeyPath());
        wxPayH5Config.setNotifyUrl(properties.getNotifyUrl());
        return wxPayH5Config;
    }
}
