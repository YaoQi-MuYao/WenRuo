package com.wenruo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "供应商")
public class Supplier {

    @Id
    @Column(name = "id")
    private Integer id;

    @ApiModelProperty(value = "用户表id")
    @Column(name = "user_id")
    private Integer userId;

    @ApiModelProperty(value = "供应商名称")
    @Column(name = "name")
    private String name;

}
