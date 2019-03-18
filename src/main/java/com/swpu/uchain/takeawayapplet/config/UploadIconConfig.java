package com.swpu.uchain.takeawayapplet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName UploadIconConfig
 * @Author hobo
 * @Date 19-3-18 下午8:07
 * @Description
 **/
@Component
@Data
@ConfigurationProperties("file")
public class UploadIconConfig {

    /**
     * 上传文件夹
     */
    private String uploadDir;


}
