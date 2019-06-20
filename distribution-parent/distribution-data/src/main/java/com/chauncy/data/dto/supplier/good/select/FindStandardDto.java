package com.chauncy.data.dto.supplier.good.select;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-06-19 11:33
 *
 * 根据商品ID和类目ID获取属性值相关信息
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "获取属性值相关信息条件")
public class FindStandardDto {

    @ApiModelProperty("商品ID")
    @NotNull(message = "商品Id不能为空")
    @NeedExistConstraint(tableName = "pm_goods")
    private Long goodsId;

    @ApiModelProperty("分类ID")
    @NotNull(message = "分类ID不能为空")
    @NeedExistConstraint(tableName = "pm_goods_category",concatWhereSql = "and level=3")
    private Long categoryId;
}
