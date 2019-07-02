package com.chauncy.data.vo.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yeJH
 * @since 2019/6/27 19:52
 */
@Data
@ApiModel(description = "资讯关联商品基本信息展示")
public class InformationRelGoodsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("关联ID")
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty(value = "分类ID")
    private Long goodsCategoryId;

    @ApiModelProperty("类目")
    private String categoryName;

    @ApiModelProperty("销售价")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "商品类型")
    private String goodsType;

}
