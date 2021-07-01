package com.wenruo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "商店")
public class Store {

    @Id
    @Column(name = "主键ID")
    private Integer id;

    @NotNull(message = "商店名称不能为空")
    @ApiModelProperty(value = "商店名称")
    @Column(name = "name")
    private String name;
}
