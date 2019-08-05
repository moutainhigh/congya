package com.chauncy.data.bo.app.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 计算返回购物券需要用到的字段
 * @Author zhangrt
 * @Date 2019/8/5 14:34
 **/
@Data
public class RewardShopTicket {

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

    //商品活动百分比
    @ApiModelProperty(hidden = true)
    private BigDecimal activityCostRate;

    //让利成本比例
    @ApiModelProperty(hidden = true)
    private BigDecimal profitsRate;

    //会员等级比例
    @ApiModelProperty(hidden = true)
    private BigDecimal purchasePresent;

    //购物券比例
    @ApiModelProperty(hidden = true)
    private BigDecimal moneyToShopTicket;



}
