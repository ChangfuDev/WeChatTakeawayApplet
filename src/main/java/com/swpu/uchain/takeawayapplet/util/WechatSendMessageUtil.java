package com.swpu.uchain.takeawayapplet.util;

import com.alibaba.fastjson.JSONObject;
import com.swpu.uchain.takeawayapplet.VO.AccessTokenVO;
import com.swpu.uchain.takeawayapplet.config.WeChatProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * @ClassName WechatSendMessageUtil
 * @Author hobo
 * @Date 19-3-28 下午1:03
 * @Description
 **/
@Slf4j
public class WechatSendMessageUtil {


    @Autowired
    private WeChatProperties weChatProperties;

    /***
     * @Author hobo
     * @Description : 获取access_token
     * @Param
     * @return java.lang.String
     **/
    public  String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + weChatProperties.getAppid() + "&secret=" + weChatProperties.getSecret();
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        AccessTokenVO accessToken = JSONObject.parseObject(response, AccessTokenVO.class);
        return accessToken.getAccessToken();
    }


    /***
     * @Author hobo
     * @Description : 发送post请求，json格式
     * @Param [url, param]
     * @return java.lang.String
     **/
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            //打开URL之间的连接
            URLConnection conn = realUrl.openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("Accept", "application");
            conn.setRequestProperty("Content-Type", "application/json");
            //设置发送post请求
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //获取URLConnection对象对应输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数
            out.print(param);
            //刷新输出流的缓冲
            out.flush();
            //用输出流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送 POST 请求发生异常", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
