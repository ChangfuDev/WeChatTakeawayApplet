package com.swpu.uchain.takeawayapplet.util.printer;

import com.swpu.uchain.takeawayapplet.dto.OrderDTO;
import com.swpu.uchain.takeawayapplet.entity.OrderDetail;
import com.swpu.uchain.takeawayapplet.enums.ResultEnum;
import com.swpu.uchain.takeawayapplet.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PrinterUtil
 * @Author hobo
 * @Date 19-4-23 上午11:11
 * @Description 小票打印机工具类
 **/
@Slf4j
public class PrinterUtil {


    private static final String url = "http://api.feieyun.cn/Api/Open/";
    public static final String user = "1056024860@qq.com";
    private static final String ukey = "IPS2fU5fNhPY3VWf";
    public static final String sn = "918503304";

    public static String printOrder(String sn, OrderDTO orderDTO) {
        String content = "";
        content = setOrderForm(orderDTO);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30000)
                .setConnectTimeout(30000)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpPost post = new HttpPost(url);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("user", user));
        String STIME = String.valueOf(System.currentTimeMillis() / 1000);
        nvps.add(new BasicNameValuePair("stime", STIME));
        nvps.add(new BasicNameValuePair("sig", signature(user, ukey, STIME)));
        nvps.add(new BasicNameValuePair("apiname", "Open_printMsg"));
        nvps.add(new BasicNameValuePair("sn", sn));
        nvps.add(new BasicNameValuePair("content", content));
        nvps.add(new BasicNameValuePair("time", "1"));

        CloseableHttpResponse response = null;
        String result = null;

        try {
            post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    result = EntityUtils.toString(httpEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            post.abort();
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return result;
    }


    private static String signature(String user, String uKey, String sTime) {
        String s = DigestUtils.sha1Hex(user + uKey + sTime);
        return s;
    }

    public static String setOrderForm(OrderDTO orderDTO) {
        log.info("OrderInfo:{}", orderDTO);
        String content = "";
        //标签说明：
        //单标签:
        //"<BR>"为换行,"<CUT>"为切刀指令(主动切纸,仅限切刀打印机使用才有效果)
        //"<LOGO>"为打印LOGO指令(前提是预先在机器内置LOGO图片),"<PLUGIN>"为钱箱或者外置音响指令
        //成对标签：
        //"<CB></CB>"为居中放大一倍,"<B></B>"为放大一倍,"<C></C>"为居中,<L></L>字体变高一倍
        //<W></W>字体变宽一倍,"<QR></QR>"为二维码,"<BOLD></BOLD>"为字体加粗,"<RIGHT></RIGHT>"为右对齐
        //拼凑订单内容时可参考如下格式
        //根据打印纸张的宽度，自行调整内容的格式，可参考下面的样例格式

        //通过POST请求，发送打印信息到服务器
        content = "<CB>颐嘉</CB><BR>";
        content += "名称　　　　　 单价  数量  小计<BR>";
        content += "--------------------------------<BR>";
        for (OrderDetail detail : orderDTO.getOrderDetails()) {
            log.info("detail:{}", detail);
            content += convert(detail.getProductName(), 13) +
                    convert(("" + detail.getProductPrice()), 8) +
                    convert(detail.getProductQuantity().toString(), 4) +
                    convert("" + (detail.getProductPrice()
                            .multiply(new BigDecimal(detail.getProductQuantity()))), 5) +
                    "<BR>";
        }
        content += "--------------------------------<BR>";
        content += "合计: " + orderDTO.getOrderAmount() + "元<BR>";
        content += "收货地址: " + orderDTO.getUserAddress() + "<BR>";
        content += "联系电话: " + orderDTO.getUserPhone() + "<BR>";
        content += "订餐时间: " + orderDTO.getCreatTime() + "<BR>";
        return content;
    }

    public static String convert(String str, int lenth) {
        log.info("Str:{},str.len{}", str, str.length());
        int len = str.length();
        if (len <= lenth) {
            for (int i = 0; i < lenth - len; i++) {
                str += " ";
            }
            return str;
        }
        throw new GlobalException(ResultEnum.PRINT_LENGTH_ERROR);
    }
}
