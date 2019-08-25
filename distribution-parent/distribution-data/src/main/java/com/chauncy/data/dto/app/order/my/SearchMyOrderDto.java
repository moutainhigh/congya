package com.chauncy.data.dto.app.order.my;

import com.chauncy.common.enums.app.order.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel(description = "查询购物车")
public class SearchMyOrderDto {

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "订单状态: NEED_PAY-未支付 NEED_SEND_GOODS-待发货 NEED_RECEIVE_GOODS-待收货 NEED_EVALUATE-待评价 ALREADY_EVALUATE-已完成\" +\n" +
            "            \"NEED_USE-待使用  ALREADY_CANCEL-已取消")
    private OrderStatusEnum  status;

}