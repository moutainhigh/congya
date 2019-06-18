package com.chauncy.data.dto.supplier.good.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-06-15 16:08
 *
 * 添加商品额外的属性值
 *
 */
@Data
@ApiModel(description = "添加商品额外的属性值")
public class AddExtraValueDto {

    @ApiModelProperty(value = "商品属性ID")
    @NotNull(message = "商品属性ID")
    private Long GoodsAttributeId;

    @ApiModelProperty(value = "商品属性值")
    @NotNull(message = "商品属性值")
    private String value;

}
