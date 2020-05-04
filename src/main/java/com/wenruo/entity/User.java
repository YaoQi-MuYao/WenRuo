package com.wenruo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: Shiro权限测试用户
 * @author: MuYao
 * @date: Created in 2020/5/4 17:43
 * @version: 1.0.0
 */
@Data
@Table(name = "user")
@ApiModel("用户")
public class User {

    @Id
    @Column(name = "id")
    private Integer  id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @ApiModelProperty(value = "角色")
    @Column(name = "role")
    private String role;

    @ApiModelProperty(value = "权限")
    @Column(name = "permission")
    private String permission;
}
