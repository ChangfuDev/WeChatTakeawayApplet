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
@Component
@ConfigurationProperties(prefix = "wechat")
public class WeChatAccountConfig {

    private String mpAppId;

    private String mpSecret;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户秘钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String keyPath;
}
