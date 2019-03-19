package com.swpu.uchain.takeawayapplet.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName CategoryForm
 * @Author hobo
 * @Date 19-3-19 下午1:03
 * @Description
 **/
@Data
public class CategoryForm {


    @NotNull(message = "类目名必填")
    @ApiModelProperty("类目名")
    private String categoryName;

    @NotNull(message = "类目编号必填")
    @ApiModelProperty("类目编号")
    private Integer categoryType;


}
