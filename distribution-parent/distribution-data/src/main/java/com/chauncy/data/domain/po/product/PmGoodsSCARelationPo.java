package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author huangwancheng
 * @create 2019-05-20 13:19
 *
 *
 * 商品、sku、类目、属性关系
 *
 * 商品、属性关系
 * goods_id
 * goods_attribute_id
 * 解决商品与品牌、标签、服务说明(平台和商家)、商品参数等属性的关系
 *
 * Sku、属性关系表
 * sku_id
 * goods_attribute_id
 * 解决sku与规格属性的关系
 *
 * 类目、属性关系表
 * goods_category_id
 * goods_attribute_id
 * 解决类目与规格、参数、服务、购买须知、活动说明等属性的关系
 *
 */
@TableName(value = "pm_goods_sku_category_attribute_relation")
@Data
public class PmGoodsSCARelationPo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "商品属性id")
    private String goodsAttributeId;

    @ApiModelProperty(value = "商品类目id")
    private String goodsCategoryId;

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "商品sku编号id")
    private String goodsSkuId;
}
