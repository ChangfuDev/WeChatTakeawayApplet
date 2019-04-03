package com.swpu.uchain.takeawayapplet.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @ClassName PayForm
 * @Author hobo
 * @Date 19-3-27 下午1:27
 * @Description
 **/
@Data
public class PayForm {

    @NotNull(message = "订单号不能为空")
    @ApiModelProperty("商家订单号")
    private Long id;

    @NotNull(message = "下单用户名不能为空")
    @ApiModelProperty("下单用户名")
    private String username;

    @NotNull(message = "下单用户手机号不能为空")
    @ApiModelProperty("下单用户手机号")
    private String userPhone;

    @NotNull(message = "用户地址不能为空")
    @ApiModelProperty("用户地址")
    private String userAddress;

    @NotNull(message = "订单总金额不能为空")
    @ApiModelProperty("订单总金额")
    private BigDecimal orderAmount;

}
