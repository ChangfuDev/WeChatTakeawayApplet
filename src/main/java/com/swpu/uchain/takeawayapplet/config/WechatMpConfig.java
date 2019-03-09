package com.swpu.uchain.takeawayapplet.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @ClassName WechatMpConfig
 * @Author hobo
 * @Date 19-3-8 下午9:40
 * @Description 微信公众号配置类
 **/
@Component
public class WechatMpConfig {


    @Autowired
    private WeChatAccountConfig weChatAccountConfig;

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        //TODO yml文件里边注入参数
        wxMpInMemoryConfigStorage.setAppId(weChatAccountConfig.getMpAppId());
        wxMpInMemoryConfigStorage.setSecret(weChatAccountConfig.getMpSecret());
        return wxMpInMemoryConfigStorage;
    }

}
