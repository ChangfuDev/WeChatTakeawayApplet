package com.swpu.uchain.takeawayapplet.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @ClassName RefundForm
 * @Author hobo
 * @Date 19-3-27 下午2:24
 * @Description
 **/
@Data
public class RefundForm {


    @NotNull(message = "商家订单编号不能为空")
    @ApiModelProperty("商家订单编号")
    private Long orderNO;

    @NotNull(message = "订单金额不能为空")
    @ApiModelProperty("订单金额")
    private BigDecimal totalFee;

    @NotNull(message = "退款金额不能为空")
    @ApiModelProperty("退款金额")
    private BigDecimal refundFee;

    @NotNull(message = "退款原因不能为空")
    @ApiModelProperty("退款原因")
    private String refundDesc;

}
