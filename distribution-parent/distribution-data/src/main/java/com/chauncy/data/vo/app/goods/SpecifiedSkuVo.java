package com.chauncy.data.vo.app.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-07-10 11:27
 *
 * sku详情
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "sku详情")
public class SpecifiedSkuVo {

    @ApiModelProperty("限购数量")
    private Integer holdQuantity;

    @ApiModelProperty("是否售空")
    private Boolean overSold;

    @ApiModelProperty("可售数量")
    private Integer sellAbleQuantity;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("划线价格")
    private BigDecimal linePrice;

    @ApiModelProperty("skuId")
    private Long skuId;

    @ApiModelProperty("图片")
    private String picture;

//    @ApiModelProperty(value = "活动")
}
