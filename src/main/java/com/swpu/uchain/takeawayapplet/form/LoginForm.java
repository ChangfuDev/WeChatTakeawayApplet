package com.swpu.uchain.takeawayapplet.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName LoginForm
 * @Author hobo
 * @Date 19-3-21 上午9:28
 * @Description
 **/
@Data
public class LoginForm {

    @NotNull(message = "openId不能为空")
    @ApiModelProperty("用户openId")
    public String openId;

}
