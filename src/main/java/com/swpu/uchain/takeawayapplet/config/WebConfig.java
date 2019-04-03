package com.swpu.uchain.takeawayapplet.config;

import com.swpu.uchain.takeawayapplet.security.AuthRoleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName WebConfig
 * @Description
 * @Author hobo
 * @Date 18-12-18 下午2:51
 **/

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthRoleInterceptor authRoleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authRoleInterceptor);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
