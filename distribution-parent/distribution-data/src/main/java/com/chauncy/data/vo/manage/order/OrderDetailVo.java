package com.chauncy.data.vo.manage.order;

import com.chauncy.common.enums.app.order.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author zhangrt
 * @Date 2019/7/25 14:33
 **/

@Data
@ApiModel(description = "订单详情")
@Accessors(chain = true)
public class OrderDetailVo {

    @ApiModelProperty("订单类型")
    private String goodsType;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单状态")
    private OrderStatusEnum status;

    @ApiModelProperty("运费")
    private BigDecimal shipMoney;

    @ApiModelProperty("订单金额")
    private BigDecimal sumMoney;

    @ApiModelProperty("优惠金额")
    // TODO: 2019/7/25 没有活动优惠金额都是0
    private BigDecimal discountMoney=BigDecimal.ZERO;

   /* @ApiModelProperty("订单金额")
    private BigDecimal sumMoney;
*/



}
