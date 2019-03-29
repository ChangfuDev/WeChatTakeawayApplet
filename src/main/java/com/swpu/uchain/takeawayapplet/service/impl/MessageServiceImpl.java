package com.swpu.uchain.takeawayapplet.service.impl;

import com.swpu.uchain.takeawayapplet.VO.TemplateDataVO;
import com.swpu.uchain.takeawayapplet.VO.WechatMsgVO;
import com.swpu.uchain.takeawayapplet.config.TemplateProperties;
import com.swpu.uchain.takeawayapplet.service.MessageService;
import com.swpu.uchain.takeawayapplet.util.TimeUtil;
import com.swpu.uchain.takeawayapplet.util.WechatSendMessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MessageServiceImpl
 * @Author hobo
 * @Date 19-3-28 下午2:51
 * @Description
 **/
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TemplateProperties templateProperties;

    private WechatSendMessageUtil wechatSendMessageUtil;

    @Override
    public void pushToBuyerPaidMsg(String openId, String formId, String orderNo, String orderAmount) {

        String accessToken = wechatSendMessageUtil.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=" + accessToken;

        //拼接推送的模板
        WechatMsgVO wechatMsgVO = new WechatMsgVO();
        wechatMsgVO.setOpenId(openId);
        wechatMsgVO.setFormid(formId);
        wechatMsgVO.setTemplateId(templateProperties.getPaidSuccess());

        Map<String, TemplateDataVO> m = new HashMap<>(5);

        TemplateDataVO keyword1 = new TemplateDataVO();
        keyword1.setValue("颐嘉外卖");
        m.put("keyword1", keyword1);

        TemplateDataVO keyword2 = new TemplateDataVO();
        keyword2.setValue(TimeUtil.getTimeCN());
        m.put("keyword2", keyword2);


        TemplateDataVO keyword3 = new TemplateDataVO();
        keyword3.setValue(orderNo);
        m.put("keyword3", keyword3);

        TemplateDataVO keyword4 = new TemplateDataVO();
        keyword4.setValue(orderAmount);
        m.put("keyword4", keyword4);

        TemplateDataVO keyword5 = new TemplateDataVO();
        keyword3.setValue("颐嘉餐厅");
        m.put("keyword5", keyword5);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, wechatMsgVO, String.class);
        log.error("小程序推送结果={}",responseEntity.getBody());

    }

    @Override
    public String pushToBuyerRefundMsg(String openId, String formId) {
        return null;
    }
}
