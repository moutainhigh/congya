package com.chauncy.data.domain.po.pay;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author huangwancheng
 * @since 2019-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pay_order")
@ApiModel( description = "支付流水号")
public class PayOrderPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "支付类型，微信 ，支付宝")
    private String payTypeCode;

    @ApiModelProperty(value = "用于微信，支付宝的  商户交易流水号， 唯一索引。")
    private String payOrderNo;

    @ApiModelProperty(value = " 业务方 支付订单号，")
    private String tradePayNo;

    @ApiModelProperty(value = "微信、支付宝返回的 给app或者网页的支付凭证，  客户端通过此信息调起支付界面。")
    private String prePayId;

    @ApiModelProperty(value = "微信支付内部订单号(transaction_id)，  支付宝内部交易号(trade_no)，  一般使用 payOrderNo,")
    private String payId;

    private String userIp;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    private Integer status;

    @ApiModelProperty(value = "如果创建订单失败，则保存第三方返回的失败错误码")
    private String errorCode;

    private String errorMsg;

    @ApiModelProperty(value = "支付申请时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "支付过期时间， 默认为2小时")
    private LocalDateTime expireTime;

    @ApiModelProperty(value = "微信为用户的openId，支付宝为buyer_id	买家支付宝用户号")
    private String openId;

    @ApiModelProperty(value = "支付宝中：买家支付宝账号")
    private String buyerLogonId;

    @ApiModelProperty(value = "回调业务方的url")
    private String notifyUrl;

    @ApiModelProperty(value = "附加信息， 支付完成后通知时候会原封不动返回业务方。")
    private String extra;

    @ApiModelProperty(value = "订单标题，微信中对应body字段，")
    private String subject;

    @ApiModelProperty(value = "订单描述，微信中对应detail字段，为json格式。 支付宝中对应 body字段，表示描述，字符串")
    private String detail;

    @ApiModelProperty(value = "二维码链接")
    private String codeUrl;

    @ApiModelProperty(value = "业务方 商户号 ,PayMerchant")
    private String merchantId;

    @ApiModelProperty(value = "TradeTypeCode，支付类型，如扫码，app支付，wap支付等。")
    private String tradeType;

    @ApiModelProperty(value = "支付成功页，  支付宝：页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问，  微信需要在前端自己设置")
    private String returnUrl;

    @ApiModelProperty(value = "退款额度，精确到分")
    private Integer refundAmount;

    @ApiModelProperty(value = "收货人")
    private String shipName;

    @ApiModelProperty(value = "收货地址")
    private String shipAddress;

    @ApiModelProperty(value = "收货人手机")
    private String phone;

    @ApiModelProperty(value = "实际应付金额")
    private BigDecimal totalRealPayMoney;


    @ApiModelProperty(value = "总优惠")
    private BigDecimal totalDiscount;

    @ApiModelProperty(value = "总运费")
    private BigDecimal totalShipMoney;

    @ApiModelProperty(value = "总税费")
    private BigDecimal totalTaxMoney;

    @ApiModelProperty(value = "总使用红包")
    private BigDecimal totalRedEnvelops;

    @ApiModelProperty(value = "总使用购物券")
    private BigDecimal totalShopTicket;

    @ApiModelProperty(value = "订单总额，包括商品(活动价格)、运费、税费")
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "总数量")
    private Integer totalNumber;

 /*   @ApiModelProperty(value = "预计奖励购物券")
    private BigDecimal totalRewardShopTicket;*/

    @ApiModelProperty(value = "购物券抵扣了多少钱")
    private BigDecimal totalShopTicketMoney;


    @ApiModelProperty(value = "红包抵扣了多少钱")
    private BigDecimal totalRedEnvelopsMoney;

    @ApiModelProperty(value = "支付用户")
    private Long umUserId;

    @ApiModelProperty(value = "收货地区")
    private String areaName;

    @ApiModelProperty(value = "是否有效订单 1-有效 0-无效")
    private Boolean enabled;




}
