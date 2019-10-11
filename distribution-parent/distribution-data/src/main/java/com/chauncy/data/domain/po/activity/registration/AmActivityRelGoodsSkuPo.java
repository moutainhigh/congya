package com.chauncy.data.domain.po.activity.registration;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 平台活动的商品与sku关联表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("am_activity_rel_goods_sku")
@ApiModel(value = "AmActivityRelGoodsSkuPo对象", description = "'平台活动的商品与sku关联表")
public class AmActivityRelGoodsSkuPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

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

    @ApiModelProperty(value = "活动价格")
    private BigDecimal activityPrice;

    @ApiModelProperty(value = "活动库存")
    private Integer activityStock;

    @ApiModelProperty(value = "活动商品ID")
    private Long goodsId;

    @ApiModelProperty(value = "活动商品对应skuID")
    private Long skuId;

    @ApiModelProperty(value = "已售数量")
    private Integer salesVolume;

    @ApiModelProperty("活动与商品关联表的ID")
    private Long relId;



}
