package com.chauncy.data.vo.app.order.cart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/9/25 17:38
 **/
@Data
@ApiModel(description = "提交订单后返回的数据")
@Accessors(chain = true)
public class SubmitOrderVo {

    @ApiModelProperty("支付单id")
    private Long payOrderId;

    @ApiModelProperty(value = "需支付多少钱")
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "需支付多少红包")
    private BigDecimal totalRedEnvelops;

    @ApiModelProperty(value = "需支付多少积分")
    private BigDecimal totalIntegral;

    @ApiModelProperty(value = "需支付多少购物券")
    private BigDecimal totalShopTicket;
}
