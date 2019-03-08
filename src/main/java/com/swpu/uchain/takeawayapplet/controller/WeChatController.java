package com.swpu.uchain.takeawayapplet.controller;

import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName WeChatController
 * @Author hobo
 * @Date 19-3-8 下午9:38
 * @Description
 **/
@RestController
@RequestMapping("/wechat")
public class WeChatController {


    @Autowired
    private WxMpService wxMpService;


    @ApiOperation("获得openId接口")
    @GetMapping("/authorize")
    public void authorize(String returnUrl){

        //TODO 调用微信sdk
    }
}
