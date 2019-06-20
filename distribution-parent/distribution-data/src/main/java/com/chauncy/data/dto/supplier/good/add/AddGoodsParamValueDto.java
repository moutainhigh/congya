package com.chauncy.data.dto.supplier.good.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2019-06-17 17:45
 *
 * 添加商品是添加商品对应的商品参数值
 */
@Data
@ApiModel(description = "添加商品是添加商品对应的商品参数值")
public class AddGoodsParamValueDto {

    @ApiModelProperty(value = "商品参数ID")
    @NeedExistConstraint(tableName = "pm_goods_attribute")
    private Long attributeId;

    @ApiModelProperty(value = "商品参数值")
    private String attributeName;
}
