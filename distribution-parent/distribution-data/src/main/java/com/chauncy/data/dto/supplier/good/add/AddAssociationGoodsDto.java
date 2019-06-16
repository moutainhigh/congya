package com.chauncy.data.dto.supplier.good.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2019-06-16 17:23
 *
 * 商品关联信息表
 */
@Data
@ApiModel(value = "AddAssociationGoodsDto", description = "商品关联信息表")
public class AddAssociationGoodsDto {

    @ApiModelProperty("商品ID")
    @NotNull(message = "商品ID不能为空")
    @NeedExistConstraint(tableName = "pm_goods")
    private Long goodsId;

    @ApiModelProperty("店铺ID")
    @NotNull(message = "店铺ID不能为空")
    @NeedExistConstraint(tableName = "sm_store")
    private Long storeId;

    @ApiModelProperty("被关联的商品ID")
    @NotNull(message = "被关联商品ID不能为空")
    @NeedExistConstraint(tableName = "pm_goods")
    private Long associatedGoodsId;

}
