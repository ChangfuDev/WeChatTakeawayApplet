package com.swpu.uchain.takeawayapplet.controller;

import com.alibaba.fastjson.JSONObject;
import com.swpu.uchain.takeawayapplet.VO.WeChatVO;
import com.swpu.uchain.takeawayapplet.config.WeChatProperties;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.exception.GlobalException;
import com.swpu.uchain.takeawayapplet.util.AesCbcUtil;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
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

    @Autowired
    private WxMpService wxOpenService;


    /**
     * @return java.lang.Object
     * @Author hobo
     * @Description : 微信登录 用于获取用户信息 要在授权之后
     * @Param [encryptedData, iv, code]
     **/
    @ApiOperation("普通用户登录授权接口")
    @GetMapping(value = "/login", name = "普通用户登录")
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


        String result = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");
        if (null != result && result.length() > 0) {
            return ResultUtil.success(result);

        }
        return ResultUtil.error(ResultEnum.DECRYPTION_FAILURE);
    }

    /**
     * @return void
     * @Author hobo
     * @Description : 可用于获取小程序的 token
     * @Param
     **/
    @GetMapping("/token")
    public void getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + weChatProperties.getAppid() + "&secret=" + weChatProperties.getSecret();
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        System.out.println(response);
        log.info("response={}", response);
    }


    @GetMapping(value = "/qrAuthorize")
    public String qrAuthorize(String returnUrl) {
        String url = "";
        String redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QrConnectScope.SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
        return "redirect:" + redirectUrl;
    }

    /***
     * @Author hobo
     * @Description : 微信扫码登录接口
     * @Param [code, returnUrl]
     * @return java.lang.String
     **/
    @ApiOperation("微信扫码登录接口")
    @GetMapping(value = "/qrUserInfo", name = "微信扫码登录接口")
    public String qrUserInfo(String code, String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new GlobalException(ResultEnum.WECHAT_MP_ERROR);
        }
        log.info("wxMpOAuth2AccessToken={}", wxMpOAuth2AccessToken);
        String openId = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:" + returnUrl + "?openid=" + openId;
    }


}
