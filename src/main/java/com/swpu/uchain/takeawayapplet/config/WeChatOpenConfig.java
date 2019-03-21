package com.swpu.uchain.takeawayapplet.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @ClassName WeChatOpenConfig
 * @Author hobo
 * @Date 19-3-20 下午8:39
 * @Description
 **/
@Component
public class WeChatOpenConfig {


    @Autowired
    private WeChatProperties weChatProperties;

    @Bean
    public WxMpService wxOpenService() {
        WxMpService wxOpenService = new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
        return wxOpenService;
    }

    @Bean
    public WxMpConfigStorage wxOpenConfigStorage() {
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(weChatProperties.getOpenAppId());
        wxMpInMemoryConfigStorage.setSecret(weChatProperties.getOpenAppSecret());
        return wxMpInMemoryConfigStorage;
    }

}
