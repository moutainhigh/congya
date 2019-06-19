package com.chauncy.data.dto.supplier.good.select;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-06-18 23:01
 *
 * 查找商品sku信息需要的条件字段
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "查找商品sku信息需要的条件字段")
public class FindSkuAttributeDto {

    @ApiModelProperty("商品id")
    @NotNull(message = "商品id不能为空")
    @NeedExistConstraint(tableName = "pm_goods")
    public Long goodsId;

    @ApiModelProperty("分类id")
    @NotNull(message = "分类id不能为空")
    @NeedExistConstraint(tableName = "pm_goods_category")
    public Long categoryId;

}
