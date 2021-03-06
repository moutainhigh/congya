package com.chauncy.data.domain.po.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品sku与属性值关联表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pm_goods_rel_attribute_value_sku")
@ApiModel(value = "PmGoodsRelAttributeValueSkuPo对象", description = "商品sku与属性值关联表")
public class PmGoodsRelAttributeValueSkuPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "商品属性值id")
    private Long goodsAttributeValueId;

    @ApiModelProperty(value = "商品sku value")
    private Long goodsSkuId;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志 1-删除 0未删除")
    @TableLogic
    private Boolean delFlag;

    public PmGoodsRelAttributeValueSkuPo(Long goodsAttributeValueId, Long goodsSkuId, String createBy) {
        this.goodsAttributeValueId = goodsAttributeValueId;
        this.goodsSkuId = goodsSkuId;
        this.createBy = createBy;
    }
}
