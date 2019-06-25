package com.chauncy.data.vo.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-06-21 15:37
 *
 * 商品信息列表
 */
@Data
@ApiModel(description = "商品信息列表")
public class PmGoodsVo {

    @ApiModelProperty("商品ID")
    private Long id;

    @ApiModelProperty("缩略图")
    private String icon;

    @ApiModelProperty("商品名称")
    private String goodName;

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

    @ApiModelProperty("销售状态")
    private Boolean publishStatus;

    @ApiModelProperty("审核状态")
    private Integer verifyStatus;

}
