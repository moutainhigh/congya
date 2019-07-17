package com.chauncy.data.vo.app.car;

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


    @ApiModelProperty(value = "可抵扣金额")
    private BigDecimal deductionMoney;

    @ApiModelProperty(value = "商品总额")
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "预计奖励购物券")
    private BigDecimal rewardShopTicket;

    @ApiModelProperty(value = "使用购物券")
    private BigDecimal shopTicket;

    @ApiModelProperty(value = "使用红包")
    private BigDecimal redEnvelops;

    @ApiModelProperty(value = "运费")
    private BigDecimal shipMoney;

    @ApiModelProperty(value = "税费")
    private BigDecimal taxMoney;

    @ApiModelProperty("根据店铺与商品类型拆单列表")
    private List<StoreOrderVo> storeOrderVos;

    @ApiModelProperty(value = "总数量")
    private int totalNumber;



}
