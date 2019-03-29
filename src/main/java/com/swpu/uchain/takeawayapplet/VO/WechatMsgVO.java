package com.swpu.uchain.takeawayapplet.VO;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName WechatMsgVO
 * @Author hobo
 * @Date 19-3-28 下午2:25
 * @Description
 **/
@Data
public class WechatMsgVO {


    /**
     * 接收消息用户openId
     */
    private String openId;

    /**
     * 模板id
     */
    private String templateId;


    /**
     * 跳转界面
     */
    private String page;


    /**
     * 收集用户的formid
     */
    private String formid;

    /**
     * 放大推送字段
     */
    private String emphasisKeyword = "keyword1.DATA";

    /**
     * 模板消息
     */
    private Map<String, TemplateDataVO> data;

}
