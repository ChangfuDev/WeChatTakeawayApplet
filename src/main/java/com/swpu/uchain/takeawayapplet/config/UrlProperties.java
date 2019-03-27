package com.swpu.uchain.takeawayapplet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName UrlProperties
 * @Author hobo
 * @Date 19-3-21 下午8:30
 * @Description
 **/
@Data
@ConfigurationProperties(prefix = "url")
@Component
public class UrlProperties {

    public String payUrl;

    public String refundUrl;

}
