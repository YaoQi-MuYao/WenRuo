package com.wenruo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @ClassName BaseUser
 * @Description TODO
 * @Author MuYao
 * @Date 2020/4/24 14:12
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户实体")
public class BaseUser {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_name")
    @NotNull(message = "用户名称不能为空")
    @ApiModelProperty(value = "用户名")
    private String userName;

    @Column(name = "name")
    @ApiModelProperty(value = "姓名")
    private String name;

    @Column(name = "password")
    @NotNull(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @Column(name = "org_id")
    @ApiModelProperty(value = "机构ID")
    private Integer orgId;

    @Column(name = "sex")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Column(name = "certificate_type")
    @ApiModelProperty(value = "证件类型")
    private String certificateType;

    @Column(name = "certificate_num")
    @ApiModelProperty(value = "证件号")
    private String certificateNum;

    @Column(name = "email")
    @Email(message = "请输入正确Email格式")
    @ApiModelProperty(value = "email")
    private String email;

    @Column(name = "type")
    @ApiModelProperty(value = "用户类型")
    private String type;

    @Column(name = "status")
    @ApiModelProperty(value = "状态")
    private String status;

    @Column(name = "tel")
    @ApiModelProperty(value = "电话")
    private String tel;

    @Column(name = "create_user")
    @ApiModelProperty(value = "创建人")
    private String createUser;

    @Column(name = "update_user")
    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @Transient
    @ApiModelProperty
    private User user;

}
