package com.chauncy.data.dto.supplier.good.add;

import com.chauncy.data.valid.annotation.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/7/10 12:52
 */
@Data
@ApiModel(value = "StoreRelGoodsStockBaseDto对象", description = "店铺分配商品库存基本信息")
public class StoreRelGoodsStockBaseDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("可判断来自哪一批次的库存")
    //@NotNull(message="parent参数不能为空")
    private Long parentId;

    @ApiModelProperty(value = "商品id")
    @NeedExistConstraint(tableName = "pm_goods")
    private Long goodsId;

    @ApiModelProperty(value = "商品规格id")
    @NeedExistConstraint(tableName = "pm_goods_sku")
    private Long goodsSkuId;

    @ApiModelProperty(value = "分配供货价")
    @NotNull(message="分配供货价不能为空")
    private BigDecimal distributePrice;

    @ApiModelProperty(value = "分配库存")
    @NotNull(message="分配库存不能为空")
    private Integer distributeStockNum;

}
