package com.chauncy.data.vo.supplier.good;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/7/9 17:00
 */
@Data
@ApiModel(description = "库存模板下商品信息")
public class StockTemplateGoodsInfoVo {

    @ApiModelProperty("模板商品关联id")
    private Long relId;

    @ApiModelProperty("商品ID")
    private Long goodsId;

    @ApiModelProperty("缩略图")
    private String icon;

    @ApiModelProperty("商品名称")
    private String goodName;

    @ApiModelProperty("品牌名称")
    private String BrandName;

    @ApiModelProperty("销售价")
    private BigDecimal sellPrice;

    @ApiModelProperty("供货价")
    private BigDecimal supplierPrice;

    @ApiModelProperty("排序")
    private BigDecimal sort;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("销量")
    private Integer salesVolume;

    @ApiModelProperty("应用标签")
    private Boolean starStatus;


}
