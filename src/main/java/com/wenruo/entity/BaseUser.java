package com.wenruo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
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
public class BaseUser {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_name")
    @NotNull(message = "用户名称不能为空")
    private String userName;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    @NotNull(message = "密码不能为空")
    private String password;

    @Column(name = "org_id")
    private Integer orgId;

    @Column(name = "sex")
    private String sex;

    @Column(name = "certificate_type")
    private String certificateType;

    @Column(name = "certificate_num")
    private String certificateNum;

    @Column(name = "email")
    @Email(message = "请输入正确Email格式")
    private String email;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "tel")
    private String tel;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

}
