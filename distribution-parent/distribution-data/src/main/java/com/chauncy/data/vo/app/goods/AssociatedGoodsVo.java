package com.chauncy.data.vo.app.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author cheng
 * @create 2019-09-22 12:23
 *
 * 关联商品--相关推荐
 */
@Data
@ApiModel(description = "关联商品--相关推荐")
@Accessors(chain = true)
public class AssociatedGoodsVo {

    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    @ApiModelProperty(value = "商品图片")
    private String picture;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "划线价格")
    private BigDecimal linePrice;

    @ApiModelProperty(value = "销量")
    private Integer salesVolume;

}
