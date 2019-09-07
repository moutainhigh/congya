package com.chauncy.data.vo.app.order.my.detail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-07-05 16:48
 *
 * 店铺下的商品
 */
@ApiModel(description = "店铺下的商品")
@Data
public class AppMyOrderDetailGoodsVo {

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("skuId")
    private Long skuId;

    @ApiModelProperty("商品快照id")
    private Long goodsTempId;

    @ApiModelProperty("sku图片")
    private String picture;

    @ApiModelProperty("sku销售价格")
    private BigDecimal sellPrice;

    @ApiModelProperty("商品规格")
    private String standardStr;

    @ApiModelProperty("数量")
    private Integer number;



}
