package com.chauncy.data.vo.supplier.good.stock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/7/11 11:18
 */
@Data
@ApiModel(description = "店铺库存模板下商品规格信息")
public class StockTemplateSkuInfoVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("关联id")
    private Long relId;

    @ApiModelProperty("可判断来自哪一批次的库存")
    private Long parentId;

    @ApiModelProperty("商品goodsId")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String goodName;

    @ApiModelProperty("商品规格id")
    private Long goodsSkuId;

    @ApiModelProperty("商品规格名称")
    private String skuName;

    /*@ApiModelProperty("商品分类")
    private String goodsCategory;*/

    @ApiModelProperty("商品类型")
    private String goodsType;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "原供货价")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "分配供货价")
    private BigDecimal distributePrice;

    @ApiModelProperty(value = "分配库存")
    private Integer distributeStockNum;

    @ApiModelProperty(value = "本批次剩余库存")
    private Integer remainingStockNum;
}
