package com.chauncy.data.vo.supplier.order;

import com.chauncy.common.enums.app.order.OrderStatusEnum;
import com.chauncy.data.vo.manage.order.list.GoodsTempVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商家端订单详情
 * @Author zhangrt
 * @Date 2019/7/25 14:33
 **/

@Data
@ApiModel(description = "商家端订单详情")
@Accessors(chain = true)
public class SmOrderDetailVo {

    @ApiModelProperty("订单类型")
    private String goodsType;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单状态")
    private OrderStatusEnum status;

    @ApiModelProperty(value = "app用户ID")
    private Long umUserId;

    /*@ApiModelProperty(value = "手机号码")
    private String phone;*/

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty("下单时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "发货时间")
    private LocalDateTime sendTime;

    @ApiModelProperty(value = "收货时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty("支付流水号")
    private Long payOrderNo;

    @ApiModelProperty("支付方式")
    private String payTypeCode;

    @ApiModelProperty("订单金额")
    private BigDecimal totalMoney;

    @ApiModelProperty("预计奖励购物券")
    private BigDecimal rewardShopTicket;

    /*@ApiModelProperty(value = "支付金额，精确到分")
    private BigDecimal payAmount;*/
    @ApiModelProperty(value = "购物券抵扣金额")
    private BigDecimal shopTicketMoney;

    @ApiModelProperty(value = "红包抵扣金额")
    private BigDecimal redEnvelopsMoney;

    @ApiModelProperty(value = "优惠券抵扣金额")
    // TODO: 2019/7/30 优惠券第二期
    private BigDecimal couponIdMoney=BigDecimal.ZERO;

    @ApiModelProperty("运费")
    private BigDecimal shipMoney;

    @ApiModelProperty(value = "税费")
    private BigDecimal taxMoney;

    @ApiModelProperty(value = "实际总付")
    private BigDecimal realMoney;

    @ApiModelProperty("优惠金额")
    // TODO: 2019/7/25 没有活动优惠金额都是0
    private BigDecimal discountMoney = BigDecimal.ZERO;

    @ApiModelProperty(value = "收货人")
    private String shipName;

    @ApiModelProperty(value = "收货手机号码")
    private String mobile;

    @ApiModelProperty(value = "收货地区")
    private String areaName;

    @ApiModelProperty(value = "详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "商品信息列表")
    private List<GoodsTempVo> goodsTempVos;

}
