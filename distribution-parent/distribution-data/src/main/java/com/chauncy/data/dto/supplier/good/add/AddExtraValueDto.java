package com.chauncy.data.dto.supplier.good.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-06-15 16:08
 *
 * 添加商品额外的规格值
 *
 */
@Data
@ApiModel(description = "添加商品额外的规格值")
public class AddExtraValueDto {

    @ApiModelProperty(value = "商品规格ID")
    @NotNull(message = "商品规格ID")
    @NeedExistConstraint(tableName = "pm_goods_attribute")
    private Long GoodsAttributeId;

    @ApiModelProperty(value = "商品规格值")
    @NotNull(message = "商品规格值")
    private String value;

}
