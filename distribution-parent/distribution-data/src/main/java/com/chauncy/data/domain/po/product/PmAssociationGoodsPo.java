package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chauncy.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 关联商品—包括关联搭配商品合关联推荐商品，外键为商品id
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pm_association_goods")
@ApiModel(value = "PmAssociationGoodsPo对象", description = "关联商品—包括关联搭配商品合关联推荐商品，外键为商品id")
public class PmAssociationGoodsPo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联商品信息ID")
    private Long id;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "被关联店铺ID")
    private Integer storeId;

    @ApiModelProperty(value = "被关联商品ID")
    private Integer associatedGoodsId;

    @ApiModelProperty(value = "关联类型 1->搭配 2->推荐")
    private Integer associationType;


}
