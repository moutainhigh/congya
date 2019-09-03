package com.chauncy.data.vo.supplier.good;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/8/10 19:51
 */
@Data
@ApiModel(description = "资讯关联商品基本信息展示")
public class RecommendGoodsVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品ID")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品分类")
    private String categoryName;

    @ApiModelProperty("销售价")
    private BigDecimal sellPrice;

    @ApiModelProperty("活动类型")
    private String goodsType;

}
