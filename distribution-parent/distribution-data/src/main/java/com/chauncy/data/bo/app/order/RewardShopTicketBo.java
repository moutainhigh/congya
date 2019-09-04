package com.chauncy.data.bo.app.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.util.BigDecimalUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 计算返回购物券需要用到的字段
 *
 * @Author zhangrt
 * @Date 2019/8/5 14:34
 **/
@Data
public class RewardShopTicketBo {

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

    //让利成本比例,goods
    @ApiModelProperty(hidden = true)
    private BigDecimal profitsRate;

    //会员等级比例
    @ApiModelProperty(hidden = true)
    private BigDecimal purchasePresent;

    //购物券比例
    @ApiModelProperty(hidden = true)
    private BigDecimal moneyToShopTicket;

    /**
     * 根据公式算出返回预计购物券
     * 没有活动和优惠券  付现价=销售价
     *
     * @return
     */
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    public BigDecimal getRewardShopTicket() {
        //固定成本=供货价+运营成本（%）+利润比例（%）（都是乘以销售价）
        BigDecimal fixedCost = BigDecimalUtil.safeAdd(supplierPrice, BigDecimalUtil.safeMultiply(transfromDecimal(operationCost), sellPrice)
                , BigDecimalUtil.safeMultiply(transfromDecimal(profitRate), sellPrice)
        );
        //活动成本=（售价-固定成本）*商品活动百分比（%）
        BigDecimal activityCost = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeDivide(sellPrice, fixedCost), transfromDecimal(activityCostRate));

        //购物券=（付现价-固定成本-活动成本）X让利成本（%）(商品详情页面)X该用户所属的会员等级反购物券比例X购物券比例设置(参数设置)

        //（付现价-固定成本-活动成本）
        BigDecimal a = BigDecimalUtil.safeSubtract(true, sellPrice, fixedCost, activityCost);

        //让利成本（%）(商品详情页面)X该用户所属的会员等级反购物券比例X购物券比例设置(参数设置)
        BigDecimal b = BigDecimalUtil.safeMultiply(BigDecimalUtil.safeMultiply(transfromDecimal(profitsRate), transfromDecimal(purchasePresent)), moneyToShopTicket);

        //购物券
        return BigDecimalUtil.safeMultiply(a, b);

    }

    /**
     * 将百分比转换成小数
     *
     * @param bigDecimal
     * @return
     */
    private BigDecimal transfromDecimal(BigDecimal bigDecimal) {
        return BigDecimalUtil.safeDivide(bigDecimal, 100);
    }

}
