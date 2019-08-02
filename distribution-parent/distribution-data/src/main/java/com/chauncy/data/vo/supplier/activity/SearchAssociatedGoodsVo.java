package com.chauncy.data.vo.supplier.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-07-26 11:05
 *
 * 商家端条件查询需要被选择参与活动的商品
 */
@Data
@ApiModel(description = "商家端条件查询需要被选择参与活动的商品")
@Accessors(chain = true)
public class SearchAssociatedGoodsVo {

    @ApiModelProperty("商品ID")
    private Long goodsId;

    @ApiModelProperty("商品图片")
    private String icon;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("售价")
    private String salePrice;

    @ApiModelProperty("活动ID")
    private Long activityId;

    @ApiModelProperty("活动名称")
    private String activityName;

}
