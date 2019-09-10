package com.chauncy.data.bo.app.order.reward;

import com.chauncy.common.util.BigDecimalUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/9/9 16:54
 **/
@Data
public class RewardRedBo {

    //销售价
    @ApiModelProperty("销售价")
    private BigDecimal sellPrice;

    //供货价
    @ApiModelProperty(hidden = true)
    private BigDecimal supplierPrice;

    //运营成本
    @ApiModelProperty(hidden = true)
    private BigDecimal operationCost;

    //利润比例
    @ApiModelProperty(hidden = true)
    private BigDecimal profitRate;

    //商品活动百分比,goods
    @ApiModelProperty(hidden = true)
    private BigDecimal activityCostRate;

    //推广成本比例,goods
    @ApiModelProperty(hidden = true)
    private BigDecimal generalizeCostRate;

    //会员红包赠送比例
    @ApiModelProperty(hidden = true)
    private BigDecimal packetPresent;

    //红包比例
    @ApiModelProperty(hidden = true)
    private BigDecimal moneyToRed;
    //购物券
    @ApiModelProperty(hidden = true)
    private BigDecimal rewardShopTicket;

    @ApiModelProperty(hidden = true)
    private Integer number;

    @ApiModelProperty(hidden = true)
    private BigDecimal skuId;


    /**
     * 根据公式算出返回红包
     * 没有活动和优惠券  付现价=销售价
     * @return
     */
    public BigDecimal calculateRed(){
        //固定成本=供货价+运营成本（%）+利润比例（%）（都是乘以销售价）
        BigDecimal fixedCost= BigDecimalUtil.safeAdd(supplierPrice,BigDecimalUtil.safeMultiply(transfromDecimal(operationCost),sellPrice)
                ,BigDecimalUtil.safeMultiply(transfromDecimal(profitRate),sellPrice)
        );
        //活动成本=（售价-固定成本）*商品活动百分比（%）
        BigDecimal activityCost=BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(sellPrice,fixedCost),transfromDecimal(activityCostRate));


        //购物券=（付现价-固定成本-活动成本-购物券）X 推广成本（%）(商品详情页面)X会员管理里面的红包赠送比例X消费商品金额的购物券比例设置（参数设置）会员等级里面的红包赠送比例要加权平分

        //（付现价-固定成本-活动成本-购物券）
        BigDecimal a=BigDecimalUtil.safeSubtract(true,sellPrice,fixedCost,activityCost,rewardShopTicket);

        //推广成本（%）(商品详情页面)X会员管理里面的红包赠送比例X消费商品金额的购物券比例设置（参数设置）会员等级里面的红包赠送比例要加权平分
        BigDecimal b=BigDecimalUtil.safeMultiply(BigDecimalUtil.safeMultiply(transfromDecimal(generalizeCostRate),transfromDecimal(packetPresent)),moneyToRed);

        //红包
        return BigDecimalUtil.safeMultiply(a,b);

    }


    /**
     * 将百分比转换成小数
     * @param bigDecimal
     * @return
     */
    private BigDecimal transfromDecimal(BigDecimal bigDecimal){
        return BigDecimalUtil.safeDivide(bigDecimal,100);
    }


}
