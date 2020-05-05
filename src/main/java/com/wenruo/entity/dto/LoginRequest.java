package com.wenruo.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: MuYao
 * @date: Created in 2020/5/5 17:11
 * @version: 1.0.0
 */
@Data
@ApiModel(value = "前端登录信息")
public class LoginRequest {

    @ApiModelProperty(value = "登录用户名", example = "superAdmin", required = true)
    private String username;

    @ApiModelProperty(value = "密码", example = "123456", required = true)
    private String password;
}
