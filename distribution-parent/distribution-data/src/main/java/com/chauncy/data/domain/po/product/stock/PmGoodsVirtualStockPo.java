package com.chauncy.data.domain.po.product.stock;

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
 * 商品虚拟库存信息表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pm_goods_virtual_stock")
@ApiModel(value = "PmGoodsVirtualStockPo对象", description = "商品虚拟库存信息表")
public class PmGoodsVirtualStockPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品虚拟库存id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "店铺id")
    private Long storeId;

    @ApiModelProperty(value = "商品id")
    private Long goodsId;

    @ApiModelProperty(value = "商品skuId")
    private Long goodsSkuId;

    @ApiModelProperty(value = "库存数量")
    private Integer stockNum;

    @ApiModelProperty(value = "是否自有商品  0 false 1true")
    private Boolean isOwn;

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
