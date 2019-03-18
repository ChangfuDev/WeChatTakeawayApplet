package com.swpu.uchain.takeawayapplet.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @ClassName ProductInfoForm
 * @Author hobo
 * @Date 19-3-18 下午6:54
 * @Description
 **/
@Data
public class ProductInfoForm {

    @NotNull(message = "商品名称必填")
    @ApiModelProperty("商品名")
    private String productName;


    @NotNull(message = "商品种类必填")
    @ApiModelProperty("商品种类")
    private Integer categoryType;

    @NotNull(message = "商品单价必填")
    @ApiModelProperty("商品单价")
    private BigDecimal productPrice;

    @NotNull(message = "商品描述必填")
    @ApiModelProperty("商品描述")
    private String productDescription;
}
