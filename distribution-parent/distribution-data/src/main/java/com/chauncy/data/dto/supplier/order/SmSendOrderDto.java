package com.chauncy.data.dto.supplier.order;

import com.chauncy.common.enums.app.order.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author zhangrt
 * @Date 2019/7/23 17:50
 **/

@Data
@ApiModel(description = "商家端订单发货")
@Accessors(chain = true)
public class SmSendOrderDto {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("商家id")
    private Long storeId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("收货人姓名")
    private String shipName;

    @ApiModelProperty("收货人手机号码")
    private String mobile;

    @ApiModelProperty("下单开始时间")
    private LocalDateTime startCreateTime;

    @ApiModelProperty("下单结束时间")
    private LocalDateTime endCreateTime;


    @ApiModelProperty("订单状态:  NEED_SEND_GOODS-待发货 NEED_RECEIVE_GOODS-发货中 NEED_EVALUATE-已收货 ")
    @NotNull(message = "订单状态不能为空！")
    private OrderStatusEnum orderStatus;

    @Min(1)
    private Integer pageNo;
    @Min(1)
    private Integer pageSize;
}
