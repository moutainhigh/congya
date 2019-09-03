package com.chauncy.data.vo.app.advice.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author cheng
 * @create 2019-08-29 14:54
 *
 * 选项卡绑定的商品信息
 *
 */
@Data
@ApiModel(description = "选项卡绑定的商品信息")
@Accessors(chain = true)
public class SearchGoodsBaseVo {

    @ApiModelProperty("商品Id")
    private Long goodsId;

    @ApiModelProperty("商品图片")
    private String picture;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品标签")
    private List<String> labelNames;

    @ApiModelProperty("划线价")
    private BigDecimal linePrice;

    @ApiModelProperty("价格")
    private BigDecimal sellPrice;

    @ApiModelProperty("销量")
    private Integer salesVolume;

}
