package com.wenruo.entity.dto;

import com.wenruo.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: MuYao
 * @date: Created in 2020/5/5 17:08
 * @version: 1.0.0
 */
@Data
@ApiModel(value = "登陆后前端接收信息")
public class LoginResponse {

    @ApiModelProperty(value = "用户信息")
    private User user;

    @ApiModelProperty(value = "令牌")
    private String token;

}
