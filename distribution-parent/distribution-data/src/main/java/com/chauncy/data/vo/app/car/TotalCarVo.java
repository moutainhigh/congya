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
@ApiModel(description = "总订单详情")
@Accessors(chain = true)
public class TotalCarVo {


    @ApiModelProperty(value = "使用葱鸭钱包可抵扣金额")
    private BigDecimal totalDeductionMoney;

    @ApiModelProperty(value = "总订单数量")
    private int totalNumber;

    @ApiModelProperty(value = "同一支付单所有订单总额，包括商品、税费、运费")
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "购物券抵扣了多少钱")
    private BigDecimal totalShopTicketMoney;

    @ApiModelProperty(value = "红包抵扣了多少钱")
    private BigDecimal totalRedEnvelopsMoney;

    @ApiModelProperty(value = "（第二版本）积分抵扣了多少钱")
    private BigDecimal totalIntegralMoney;

    @ApiModelProperty(value = "总订单应付总额")
    private BigDecimal totalRealPayMoney;

   /* @ApiModelProperty(value = "总订单预计奖励购物券")
    private BigDecimal totalRewardShopTicket;*/

/*    @ApiModelProperty(value = "总订单使用购物券")
    private BigDecimal totalShopTicket;*/



   /* @ApiModelProperty(value = "总订单使用红包")
    private BigDecimal totalRedEnvelops;*/



    @ApiModelProperty(value = "总订单运费")
    private BigDecimal totalShipMoney;

    @ApiModelProperty(value = "总订单税费")
    private BigDecimal totalTaxMoney;

    @ApiModelProperty("根据店铺与商品类型拆单列表")
    private List<StoreOrderVo> storeOrderVos;

    @ApiModelProperty(value = "是否需要实名认证")
    private Boolean needCertification;


    //第二版本
    @ApiModelProperty(value = "满减优惠券扣去了多少钱")
    private BigDecimal reduceCouponMoney;

    //第二版本
    @ApiModelProperty(value = "满减活动扣去了多少钱")
    private BigDecimal fullDiscountMoney;

    //第二版本
    @ApiModelProperty(value = "拼团优惠多少元")
    private BigDecimal groupDiscountMoney;

//    //第二版本
//    @ApiModelProperty(value = "参与的优惠类型：0-无活动 1-满减 2-积分 3-秒杀 4-拼团 5-包邮券 6-满减券 7-折扣券")
//    private Integer activityType=0;

 //第二版本
    @ApiModelProperty(value = "优惠券信息")
    private String couponMessage;



  /*  @ApiModelProperty(value = "总订单合计优惠")
    private BigDecimal totalDiscount;*/



      //计算出应付金额=商品总额+运费+税费-购物券和红包抵扣的金额
    public BigDecimal calculationRealPayMoney(){
        return BigDecimalUtil.safeSubtract(totalMoney,totalDeductionMoney);
    }





}
