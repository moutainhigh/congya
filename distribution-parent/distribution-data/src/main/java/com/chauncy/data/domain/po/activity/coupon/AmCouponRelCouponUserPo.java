package com.chauncy.data.domain.po.activity.coupon;

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
 * 优惠券和用户关联表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("am_coupon_rel_coupon_user")
@ApiModel(value = "AmCouponRelCouponUserPo对象", description = "优惠券和用户关联表")
public class AmCouponRelCouponUserPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "领取时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "已领数量")
    private Integer receiveNum;

    @ApiModelProperty(value = "优惠券是否已使用:1-已使用 2-未使用  3-失效")
    private Integer useStatus;

    @ApiModelProperty(value = "用户有的优惠券的类型，1--领取 2--购买")
    private Integer type;

    @ApiModelProperty(value = "截止日期")
    private LocalDateTime deadLine;
}
