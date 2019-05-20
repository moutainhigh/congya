package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author huangwancheng
 * @create 2019-05-20 12:53
 *
 * 商品属性值
 *
 * 规格值
 * 商品参数值
 *
 */
@Data
@TableName(value = "tb_goods_attribute_value")
public class GoodsAttributeValue implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "属性ID")
    private int productAttributeId;

    @ApiModelProperty(value = "属性值(参数值材质或规格值)")
    private int value;

    @ApiModelProperty(value = "上市时间")
    private int listingDate;
}
