package com.chauncy.data.domain.po.order;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * 商品快照快照
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_goods_temp")
@ApiModel(value = "OmOrderTempPo对象", description = "订单快照")
public class OmGoodsTempPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单快照id")
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

    @ApiModelProperty(value = "商品数量")
    private Integer number;

    @ApiModelProperty(value = "缩略图")
    private String icon;

    @ApiModelProperty(value = "规格")
    private String standardStr;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品销售价")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "支付单id")
    private Long payOrderId;


}
