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
    private String name;

    @NotNull(message = "手机号必填")
    @ApiModelProperty("客户手机号码")
    private String phone;

    @NotNull(message = "地址必填")
    @ApiModelProperty("客户地址")
    private String address;

    @NotNull(message = "openId必填")
    @ApiModelProperty("微信openId")
    private String openId;

    @NotNull(message = "购物车不能为空")
    @ApiModelProperty("购物车")
    private String items;

}
