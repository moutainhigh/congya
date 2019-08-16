package com.chauncy.data.bo.app.order.my;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单完成后返回的购物券、积分、经验值
 * @Author zhangrt
 * @Date 2019/8/13 14:15
 **/
@Data
public class OrderRewardBo {

    @ApiModelProperty("预计奖励购物券")
    private BigDecimal rewardShopTicket;

    @ApiModelProperty("预计返积分")
    private BigDecimal rewardIntegral;

    @ApiModelProperty("预计返经验值")
    private BigDecimal rewardExperience;
}
