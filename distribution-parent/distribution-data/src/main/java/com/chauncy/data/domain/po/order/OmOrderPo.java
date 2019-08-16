package com.chauncy.data.domain.po.order;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.chauncy.common.enums.app.order.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-09
 */
@Data
@ToString
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

    @ApiModelProperty(value = "评价状态: 1--待评价 2--已评价 3--还不能评价")
    private Integer evaluateStatus;

    @ApiModelProperty(value = "店铺ID")
    private Long storeId;

    @ApiModelProperty(value = "用户所属店铺id")
    private Long userStoreId;

    @ApiModelProperty(value = "订单状态:0-待付款 1-待发货 2-待收货 3-待评价 4-已完成 5-待使用 6-已使用 7-已取消")
    private OrderStatusEnum status;

    @ApiModelProperty(value = "活动类型0-无 1-秒杀 2-拼团 3-积分 4-满减")
    private Integer activityType;

    @ApiModelProperty(value = "订单总金额，包括运费、税费、商品")
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "商品数量")
    private Integer totalNumber;

    @ApiModelProperty(value = "订单商品类型  0-普通 1-自取 2-海外直邮 3-保税仓 4-服务类 5-虚拟商品 ")
    private String goodsType;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "发货时间")
    private LocalDateTime sendTime;

    @ApiModelProperty(value = "收货时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty(value = "取消时间")
    private LocalDateTime closeTime;

    @ApiModelProperty("售后截止时间")
    private LocalDateTime afterSaleDeadline;

    @ApiModelProperty(value = "备注")
    private String remark;


    @ApiModelProperty(value = "运费")
    private BigDecimal shipMoney;

    @ApiModelProperty(value = "税费")
    private BigDecimal taxMoney;


    @ApiModelProperty(value = "总支付单id")
    private Long payOrderId;

    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "店铺收入配置比例")
    private BigDecimal incomeRate;


    @ApiModelProperty(value = "使用红包")
    private BigDecimal redEnvelops;

    @ApiModelProperty(value = "使用购物券")
    private BigDecimal shopTicket;

    @ApiModelProperty(value = "红包抵扣金额")
    private BigDecimal shopTicketMoney;

    @ApiModelProperty(value = "购物券抵扣金额")
    private BigDecimal redEnvelopsMoney;

    @ApiModelProperty(value = "应付金额")
    private BigDecimal realMoney;

    @ApiModelProperty(value = "实名认证id")
    private Long realUserId;

    @ApiModelProperty(value = "二维码")
    private String qRCode;


}
