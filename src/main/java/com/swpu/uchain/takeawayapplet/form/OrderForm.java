package com.swpu.uchain.takeawayapplet.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @ClassName OrderForm
 * @Author hobo
 * @Date 19-3-7 上午11:28
 * @Description
 **/
@Data
public class OrderForm {

    @NotNull(message = "姓名必填")
    @ApiModelProperty("客户名")
    private String userName;

    @NotNull(message = "手机号必填")
    @ApiModelProperty("客户手机号码")
    private String userPhone;

    @NotNull(message = "地址必填")
    @ApiModelProperty("客户地址")
    private String userAddress;

    @ApiModelProperty("微信openId,由code带入")
    private String openId;

    @NotNull(message = "购物车不能为空")
    @ApiModelProperty("购物车")
    private String items;

}
