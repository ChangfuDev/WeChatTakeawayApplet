package com.swpu.uchain.takeawayapplet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName TemplateProperties
 * @Author hobo
 * @Date 19-3-28 下午3:05
 * @Description 模板id对应配置类
 **/
@Data
@ConfigurationProperties(prefix = "template")
@Component
public class TemplateProperties {

   private String paidSuccess;
}
