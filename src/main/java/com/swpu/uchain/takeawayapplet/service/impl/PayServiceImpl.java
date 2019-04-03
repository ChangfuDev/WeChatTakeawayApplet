package com.swpu.uchain.takeawayapplet.service.impl;

import com.swpu.uchain.takeawayapplet.VO.ResultVO;
import com.swpu.uchain.takeawayapplet.config.UrlProperties;
import com.swpu.uchain.takeawayapplet.config.WeChatProperties;
import com.swpu.uchain.takeawayapplet.dto.OrderDTO;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.exception.GlobalException;
import com.swpu.uchain.takeawayapplet.form.PayForm;
import com.swpu.uchain.takeawayapplet.form.RefundForm;
import com.swpu.uchain.takeawayapplet.service.MessageService;
import com.swpu.uchain.takeawayapplet.service.OrderService;
import com.swpu.uchain.takeawayapplet.service.PayService;
import com.swpu.uchain.takeawayapplet.util.GetOpenIdUtil;
import com.swpu.uchain.takeawayapplet.util.PayUtil;
import com.swpu.uchain.takeawayapplet.util.RandomUtil;
import com.swpu.uchain.takeawayapplet.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

//import com.swpu.uchain.takeawayapplet.service.MessageService;

/**
 * @ClassName PayServiceImpl
 * @Author hobo
 * @Date 19-3-21 下午8:11
 * @Description
 **/
@Service
@Slf4j
public class PayServiceImpl implements PayService {


    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UrlProperties urlProperties;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageService messageService;


    /**
     * @return void
     * @Author hobo
     * @Description : 创建预支付请求
     * @Param [orderDTO]
     **/
    @Override
    public ResultVO creat(PayForm payForm, String code, HttpServletRequest request) {
        try {
            //生成随机字符串
            String nonceStr = RandomUtil.getRandomStringByLength(32);
            //商品简介
            String body = new String(weChatProperties.getTitle().getBytes("ISO-8859-1"), "UTF-8");
            //获得本机的ip地址
            String spbillCreatIp = PayUtil.getIpAddr(request);
            String ordNo = payForm.getId() + "";
            //支付金额  单位：分  转为元
            String totalFee = payForm.getOrderAmount().multiply(new BigDecimal(100)) + "";
            String openId = GetOpenIdUtil.getOpenId(code);
            //生成预支付订单
            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", weChatProperties.getAppid());
            packageParams.put("mch_id", weChatProperties.getMchId());
            packageParams.put("nonce_str", nonceStr);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", ordNo);
            packageParams.put("totalFee", totalFee);
            packageParams.put("spbill_create_ip", spbillCreatIp);
            packageParams.put("notify_url", weChatProperties.getNotifyUrl());
            packageParams.put("trade_type", weChatProperties.getTradeType());
            packageParams.put("open_id", openId);

            //除去数组中的空值和签名参数
            packageParams = PayUtil.paraFileter(packageParams);
            String preStr = PayUtil.createLinkString(packageParams);

            //MD5运算生成签名，第一次签名 调用统一下单接口
            String mySign = PayUtil.sign(preStr, weChatProperties.getMchKey(), "utf-8").toUpperCase();
            log.info("====================第一次签名" + mySign + "====================");

            //连同生成的签名一起拼接成xml数据
            String xml = "</xml version='1.0' encoding='gdk'>"
                    + "<appid>" + weChatProperties.getAppid() + "</appid>"
                    + "<body><![CDATA]" + body + "]]></body>"
                    + "<mch_id>" + weChatProperties.getMchId() + "</mch_id>"
                    + "<nonce_str>" + nonceStr + "</nonce_str>"
                    + "<openid>" + openId + "</openid>"
                    + "<out_trade_no>" + ordNo + "<out_trade_no>"
                    + "<spbill_create_ip>" + spbillCreatIp + "</spbill_create_ip>"
                    + "<total_fee>" + totalFee + "</total_fee>"
                    + "<trade_type>" + weChatProperties.getTradeType() + "</trade_type>"
                    + "<sign>" + mySign + "</sign>"
                    + "</xml>";

            log.info("====================调试请求XML数据====================");
            System.out.println(xml);

            //调用统一下单接口
            String result = PayUtil.httpRequest(urlProperties.getPayUrl(), "POST", xml);

            log.info("====================调试返回XML数据====================");
            System.out.println(result);

            // 将解析结果放入HashMap中
            Map map = PayUtil.doXMLParse(result);
            String return_code = (String) map.get("return_code");

            //返回移动端需要的参数
            Map<String, Object> response = new HashMap<String, Object>();
            //return_code 为成功　　return_msg 为失败
            if ("SUCCESS".equals(return_code)) {
                //业务结果
                String prepay_id = (String) map.get("prepay_id");
                response.put("nonceStr", nonceStr);
                response.put("package", "prepay_id" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                //时间 类型转换为微信官方要求的String
                response.put("time_stamp", timeStamp + "");

                String stringSignTemp = "appId=" + weChatProperties.getAppid() + "&nonceStr=" + nonceStr
                        + "&package=prepay_id" + prepay_id + "&signType=MD5" + "&timeStamp=" + timeStamp;
                //再次签名 用于小程序端调用wx.requestPayment方法
                String paySign = PayUtil.sign(stringSignTemp, weChatProperties.getMchKey(), "utf-8").toUpperCase();
                log.info("===================第二次签名：" + paySign + "====================");

                response.put("paySign", paySign);
            }
            response.put("appid", weChatProperties.getAppid());
            return ResultUtil.success(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.PAY_REQUEST_FAIL);
        }
    }

    /**
     * @return void
     * @Author hobo
     * @Description :  异步通知判断签名
     * @Param [notifyData]
     **/
    @Override
    public ResultVO notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        String notifyXml = sb.toString();
        String resXml = "";
        log.info("接收到的报文 notifyXml={}", notifyXml);
        Map map = PayUtil.doXMLParse(notifyXml);

        //从报文中获取值
        String returnCode = (String) map.get("return_code");
        String outTradeNo = (String) map.get("out_trade_no");
        String totalFee = (String) map.get("total_fee");


        Long orderId = Long.parseLong(outTradeNo);
        OrderDTO orderDTO = orderService.findOrder(orderId);

        if (!(orderDTO.getOrderAmount() + "").equals(totalFee)) {
            throw new GlobalException(ResultEnum.AMOUNT_ERROR);
        }

        if ("SUCCESS".equals(returnCode)) {
            //验证签名是否正确
            if (PayUtil.verify(PayUtil.createLinkString(map), (String) map.get("sign"), weChatProperties.getMchKey(), "utf-8")) {
                String prepay_id = (String) map.get("prepay_id");

                //通知微信服务器已经支付成功
                resXml = "<xml>"
                        + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>"
                        + "</xml>";
                orderService.paidOrder(orderDTO);
                messageService.pushToBuyerPaidMsg(orderDTO.getOpenId(), prepay_id, orderDTO.getId() + "", totalFee);
                return ResultUtil.success();
            } else {
                resXml = "<xml>"
                        + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>"
                        + "</xml>";
            }
            System.out.println(resXml);
            log.info("微信支付回调数据结束");
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
            return ResultUtil.error(ResultEnum.PAY_FILE);

        }
        return ResultUtil.error(ResultEnum.PARAMETER_NOT_MATCH);
    }

    /**
     * @return void
     * @Author hobo
     * @Description : 退款
     * @Param [orderDTO]
     **/
    @Override
    public ResultVO refund(RefundForm refundForm) {
        try {
            String nonceStr = RandomUtil.getRandomStringByLength(32);

            String totalFee = refundForm.getTotalFee().multiply(new BigDecimal(100)) + "";
            String refundFee = refundForm.getRefundFee().multiply(new BigDecimal(100)) + "";

            Map<String, String> paramMap = new HashMap<String, String>();

            paramMap.put("appid", weChatProperties.getAppid());
            paramMap.put("mch_id", weChatProperties.getMchId());
            paramMap.put("nonce_str", nonceStr);
            //商家订单号
            paramMap.put("out_trade_no", refundForm.getOrderNO() + "");
            //订单总金额
            paramMap.put("total_fee", totalFee);
            //退款金额
            paramMap.put("refund_fee", refundFee);
            //退款原因
            paramMap.put("refund_desc", refundForm.getRefundDesc());

            paramMap = PayUtil.paraFileter(paramMap);
            String preStr = PayUtil.createLinkString(paramMap);


            String mySign = PayUtil.sign(preStr, weChatProperties.getMchKey(), "utf-8").toUpperCase();
            log.info("====================第一次签名" + mySign + "====================");

            //组装xml
            String xml = "<xml>"
                    + "<appid>" + weChatProperties.getAppid() + "</appid>"
                    + "<mch_id>" + weChatProperties.getMchId() + "</mch_id>"
                    + "<nonce_str>" + nonceStr + "</nonce_str>"
                    + "<out_trade_no>" + refundForm.getOrderNO() + "</out_trade_no>"
                    + "<refund_fee>" + refundFee + "</refund_fee>"
                    + "<total_fee>" + totalFee + "</total_fee>"
                    + "<refund_desc>" + refundForm.getRefundDesc() + "</refund_desc>"
                    + "<sign>" + mySign + "</sign>"
                    + "</xml>";
            log.info("====================调试请求XML数据====================");
            System.out.println(xml);

            //调用退款接口
            String result = PayUtil.httpRequest(urlProperties.getRefundUrl(), "POST", xml);

            log.info("====================调试返回XML数据====================");
            System.out.println(result);

            Map map = PayUtil.doXMLParse(result);
            String return_code = (String) map.get("return_code");
            Map<String, Object> response = new HashMap<String, Object>();
            //return_code 为成功　　return_msg 为失败
            if ("SUCCESS".equals(return_code)) {
                //业务结果
                String prepay_id = (String) map.get("prepay_id");
                response.put("nonceStr", nonceStr);
                response.put("package", "prepay_id" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                //时间 类型转换为微信官方要求的String
                response.put("time_stamp", timeStamp + "");

                String stringSignTemp = "appId=" + weChatProperties.getAppid() + "&nonceStr=" + nonceStr
                        + "&package=prepay_id" + prepay_id + "&signType=MD5" + "&timeStamp=" + timeStamp;
                //再次签名 用于小程序端调用wx.requestPayment方法
                String paySign = PayUtil.sign(stringSignTemp, weChatProperties.getMchKey(), "utf-8").toUpperCase();
                log.info("===================第二次签名：" + paySign + "====================");

                response.put("paySign", paySign);
            }
            response.put("appid", weChatProperties.getAppid());
            return ResultUtil.success(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
