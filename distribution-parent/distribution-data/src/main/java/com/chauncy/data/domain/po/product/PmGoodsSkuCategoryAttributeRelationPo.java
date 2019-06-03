package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.chauncy.common.constant.SecurityConstant;
import com.chauncy.common.util.serializer.LongJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 商品、sku、类目、属性关系表
 * <p>
 * 商品、属性关系
 * goods_id
 * goods_attribute_id
 * 解决商品与品牌、标签、服务说明(平台和商家)、商品参数等属性的关系
 * <p>
 * Sku、属性关系表
 * sku_id
 * goods_attribute_id
 * 解决sku与规格属性的关系
 * <p>
 * 类目、属性关系表
 * goods_category_id
 * goods_attribute_id
 * 解决类目与规格、参数、服务、购买须知、活动说明等属性的关系
 * </p>
 *
 * @author huangwancheng
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pm_goods_sku_category_attribute_relation")
@ApiModel(value = "PmGoodsSkuCategoryAttributeRelationPo对象", description = "商品、sku、类目、属性关系表 ")
public class PmGoodsSkuCategoryAttributeRelationPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;

    @ApiModelProperty(value = "商品属性ID")
    private Long goodsAttributeId;

    @ApiModelProperty(value = "商品属性值ID")
    private Long attributeValueId;

    @ApiModelProperty(value = "商品类目ID")
    private Long goodsCategoryId;

    @ApiModelProperty(value = "商品id ")
    private Long goodsId;

    @ApiModelProperty(value = "商品sku的ID")
    private Long goodsSkuId;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @LastModifiedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 默认0")
    private Boolean delFlag;


}
