package com.chauncy.data.domain.po.order;

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
 * 订单快照
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("om_order_temp")
@ApiModel(value = "OmOrderTempPo对象", description = "订单快照")
public class OmOrderTempPo implements Serializable {

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
    private Boolean delFlag;

    @ApiModelProperty(value = "订单状态")
    private String status;

    @ApiModelProperty(value = "收货人")
    private String shipName;

    @ApiModelProperty(value = "收货地址")
    private String shipAddress;

    @ApiModelProperty(value = "供应商")
    private String storeName;

    @ApiModelProperty(value = "收货人手机")
    private String phone;

    @ApiModelProperty(value = "订单商品详情，json格式")
    private String goods;

    @ApiModelProperty(value = "商品类型")
    private String goodsType;

    @ApiModelProperty(value = "商品总额")
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "预计奖励购物券")
    private BigDecimal rewardShopTicket;

    @ApiModelProperty(value = "购物券")
    private BigDecimal shopTicket;

    @ApiModelProperty(value = "优惠券")
    private BigDecimal coupon;

    @ApiModelProperty(value = "红包")
    private BigDecimal redEnvelops;

    @ApiModelProperty(value = "运费")
    private BigDecimal shipMoney;

    @ApiModelProperty(value = "税费")
    private BigDecimal taxMoney;

    @ApiModelProperty(value = "实际付款")
    private BigDecimal realPayMoney;

    @ApiModelProperty(value = "总优惠")
    private BigDecimal totalDiscount;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "下单时间")
    private LocalDateTime orderTime;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "支付流水号")
    private String tradePayNo;

    @ApiModelProperty(value = "发货时间")
    private LocalDateTime sendTime;


}
