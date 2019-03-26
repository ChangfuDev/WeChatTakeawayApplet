package com.swpu.uchain.takeawayapplet.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName UserForm
 * @Author hobo
 * @Date 19-3-26 下午5:09
 * @Description
 **/
@Data
public class UserForm {

    @NotNull(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String username;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;
}
