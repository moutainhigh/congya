package com.chauncy.data.domain.po.order;

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
 * 订单表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_order")
@ApiModel(value = "OmOrderPo对象", description = "订单表")
public class OmOrderPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
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
    private Boolean delFlag;

    @ApiModelProperty(value = "app用户ID")
    private Long umUserId;

    @ApiModelProperty(value = "收货地址ID")
    private Long areaShippingId;

    @ApiModelProperty(value = "评价状态: 1--待评价 2--已评价")
    private Integer evaluateStatus;

    @ApiModelProperty(value = "店铺ID")
    private Long storeId;
}
