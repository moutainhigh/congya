package com.chauncy.data.vo.supplier.good;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

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
    private String goodsName;

    @ApiModelProperty("活动类型")
    private String activityType;

    @ApiModelProperty("商品分类")
    private String categoryName;

}
