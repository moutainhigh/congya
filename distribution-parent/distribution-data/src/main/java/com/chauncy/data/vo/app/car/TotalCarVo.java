package com.chauncy.data.vo.app.car;

import com.chauncy.common.util.BigDecimalUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zhangrt
 * @Date 2019/7/10 11:02
 **/
@Data
@ApiModel(description = "单个订单详情")
@Accessors(chain = true)
public class TotalCarVo {


    @ApiModelProperty(value = "总订单可抵扣金额")
    private BigDecimal totalDeductionMoney;

    @ApiModelProperty(value = "总订单商品总额")
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "总订单预计奖励购物券")
    private BigDecimal totalRewardShopTicket;

    @ApiModelProperty(value = "总订单使用购物券")
    private BigDecimal totalShopTicket;

    @ApiModelProperty(value = "购物券抵扣了多少钱")
    private BigDecimal totalShopTicketMoney;

    @ApiModelProperty(value = "总订单使用红包")
    private BigDecimal totalRedEnvelops;

    @ApiModelProperty(value = "红包抵扣了多少钱")
    private BigDecimal totalRedEnvelopsMoney;

    @ApiModelProperty(value = "总订单运费")
    private BigDecimal totalShipMoney;

    @ApiModelProperty(value = "总订单税费")
    private BigDecimal totalTaxMoney;

    @ApiModelProperty("根据店铺与商品类型拆单列表")
    private List<StoreOrderVo> storeOrderVos;

    @ApiModelProperty(value = "总订单数量")
    private int totalNumber;

    @ApiModelProperty(value = "总订单合计优惠")
    private BigDecimal totalDiscount;

    @ApiModelProperty(value = "总订单应付总额")
    private BigDecimal totalRealPayMoney;





}
