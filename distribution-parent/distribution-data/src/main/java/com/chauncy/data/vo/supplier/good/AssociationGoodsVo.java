package com.chauncy.data.vo.supplier.good;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/12  10:45
 * @Version 1.0
 */
@Data
@ApiModel (value = "获取商品已经关联的商品信息")
public class AssociationGoodsVo {

    @ApiModelProperty("id")
    @NeedExistConstraint (tableName = "pm_association_goods")
    private Long id;

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品销售价")
    private String sellPrice;

    @ApiModelProperty("店铺id")
    private Long storeId;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("商品分类")
    private String category;
}
