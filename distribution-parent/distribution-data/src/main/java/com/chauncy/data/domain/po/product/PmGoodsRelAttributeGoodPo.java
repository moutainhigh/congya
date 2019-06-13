package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品与属性关联表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pm_goods_rel_attribute_good")
@ApiModel(value = "PmGoodsRelAttributeGoodPo对象", description = "商品与属性关联表")
public class PmGoodsRelAttributeGoodPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "商品属性id")
    private Long goodsAttributeId;

    @ApiModelProperty(value = "商品 id")
    private Long goodsGoodId;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    private Boolean delFlag;


}
