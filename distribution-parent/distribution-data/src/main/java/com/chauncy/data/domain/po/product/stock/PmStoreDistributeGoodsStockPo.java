package com.chauncy.data.domain.po.product.stock;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 店铺分配库存信息表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pm_store_distribute_goods_stock")
@ApiModel(value = "PmStoreDistributeGoodsStockPo对象", description = "店铺分配库存信息表")
public class PmStoreDistributeGoodsStockPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "店铺分配库存信息表id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "店铺库存id")
    private Long storeStockId;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "商品规格id")
    private Long goodsSkuId;

    @ApiModelProperty(value = "分配供货价")
    private BigDecimal distributePrice;

    @ApiModelProperty(value = "分配库存")
    private Integer distributeStockNum;

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
