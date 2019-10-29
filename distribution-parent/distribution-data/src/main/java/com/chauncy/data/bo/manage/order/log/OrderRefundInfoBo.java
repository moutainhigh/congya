package com.chauncy.data.bo.manage.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yeJH
 * @since 2019/9/4 15:53
 */
@Data
@ApiModel(value = "OrderRefundInfoBo", description = "退款微信订单号、商户订单号二选一")
public class OrderRefundInfoBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户订单号")
    private Long payOrderId;

    @ApiModelProperty(value = "用于微信，支付宝的  商户交易流水号， 唯一索引。")
    private String payOrderNo;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal totalRealPayMoney;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundMoney;

}
