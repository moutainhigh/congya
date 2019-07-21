package com.chauncy.data.domain.po.activity.gift;

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
 * 礼包表与优惠券关联表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("am_gift_rel_gift_coupon")
@ApiModel(value = "AmGiftRelGiftCouponPo对象", description = "礼包表与优惠券关联表")
public class AmGiftRelGiftCouponPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "礼包ID")
    private Long giftId;

    @ApiModelProperty(value = "优惠券ID")
    private Long couponId;


}
