package com.swpu.uchain.takeawayapplet.controller;

import com.alibaba.fastjson.JSONObject;
import com.swpu.uchain.takeawayapplet.VO.WeChatVO;
import com.swpu.uchain.takeawayapplet.config.WeChatProperties;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.util.AesCbcUtil;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WxController
 * @Author hobo
 * @Date 19-3-14 下午1:38
 * @Description
 **/

@RestController
@RequestMapping("/takeaway/user")
@Slf4j
@Api(tags = "微信端接口")
public class WxController {

    @Autowired
    private WeChatProperties weChatProperties;


    /**
     * @Author hobo
     * @Description : 微信登录 用于获取用户信息 要在授权之后
     * @Param [encryptedData, iv, code]
     * @return java.lang.Object
     **/
    @GetMapping("/login")
    public Object getUserInfo(String encryptedData, String iv, String code) {
        Map map = new HashMap();
        if (code == null) {
            return ResultUtil.error(ResultEnum.CODE_EMPTY);
        }

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + weChatProperties.getAppid() + "&secret=" + weChatProperties.getSecret() + "&js_code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        WeChatVO weChatVO = JSONObject.parseObject(response, WeChatVO.class);
        String openId = weChatVO.getOpenId();
        String sessionKey = weChatVO.getSessionKey();


        String result = AesCbcUtil.decrypt(encryptedData,sessionKey,iv,"UTF-8");
        if (null!=result&&result.length()>0){
            //TODO 将用户信息部分插入数据库 不返回给前端参数
            return ResultUtil.success(result);

        }
        return ResultUtil.error(ResultEnum.DECRYPTION_FAILURE);
    }

    /**
     * @Author hobo
     * @Description : 可用于获取小程序的 token
     * @Param
     * @return void
     **/
    @GetMapping("/token")
    public void getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + weChatProperties.getAppid() + "&secret=" + weChatProperties.getSecret();
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        System.out.println(response);
        log.info("response={}", response);
    }


}
