package com.chauncy.data.bo.supplier.good;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author cheng
 * @create 2019-06-19 12:53
 *
 * 商品属性值Bo
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "返回的ID与名称或值")
public class GoodsValueBo {

    private Long id;

    @ApiModelProperty("字段的名称或值")
    private String name;

    @ApiModelProperty("是否为自定义属性")
    private Boolean isCustom;
}