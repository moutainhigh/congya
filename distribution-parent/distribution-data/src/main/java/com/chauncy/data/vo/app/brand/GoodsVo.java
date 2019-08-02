package com.chauncy.data.vo.app.brand;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author: HUANGWANCHENG
 * @Date: 2019/07/15  21:50
 * @Version 1.
 *
 * 品牌的商品基础信息
 */
@Data
@ApiModel(description = "品牌的商品基础信息")
@Accessors(chain = true)
public class GoodsVo {

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("销量")
    private Integer salesVolume;

    @ApiModelProperty("销售价格")
    private BigDecimal salePrice;

    @ApiModelProperty("划线价格")
    private BigDecimal linePrice;

    @ApiModelProperty("活动类型")
    private String activityType;
}
