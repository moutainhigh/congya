package com.chauncy.data.vo.app.advice.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-09-03 16:09
 *
 * 有品选项卡下品牌下的商品信息
 */
@Data
@ApiModel(description = "有品选项卡下品牌下的商品信息")
@Accessors(chain = true)
public class BrandGoodsVo {

    @ApiModelProperty("商品ID")
    private Long goodsId;

    @ApiModelProperty("skuID")
    private Long skuId;

    @ApiModelProperty("商品sku对应的图片")
    private String picture;

    @ApiModelProperty("商品划线价格")
    private BigDecimal linePrice;

    @ApiModelProperty("sku对应的销售价格")
    private BigDecimal sellPrice;

    @ApiModelProperty("销量")
    private Integer salesVolume;

}
