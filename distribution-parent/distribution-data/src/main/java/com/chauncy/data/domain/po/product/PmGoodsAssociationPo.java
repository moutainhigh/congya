package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author huangwancheng
 * @create 2019-05-20 13:23
 *
 * 关联商品—
 *
 * 关联搭配商品
 * 关联推荐商品
 *
 */
@Data
@TableName(value = "pm_association_goods")
public class PmGoodsAssociationPo
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "被关联店铺id")
    private String storeId;

    @ApiModelProperty(value = "被关联商品id")
    private String associatedGoodsId;

    @ApiModelProperty(value = "关联类型 1->搭配 2->推荐")
    private String associationType;
}
