package com.chauncy.data.dto.supplier.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.common.enums.app.activity.type.ActivityTypeEnum;
import com.chauncy.common.enums.app.order.OrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author zhangrt
 * @Date 2019/7/23 17:50
 **/

@Data
@ApiModel(description = "商家端搜索订单列表")
@Accessors(chain = true)
public class SmSearchOrderDto {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    private Long storeId;


    @ApiModelProperty("下单开始时间")
    private LocalDateTime startCreateTime;

    @ApiModelProperty("下单结束时间")
    private LocalDateTime endCreateTime;

    @ApiModelProperty("订单更新开始时间")
    private LocalDateTime startUpdateTime;

    @ApiModelProperty("订单更新结束时间")
    private LocalDateTime endUpdateTime;

    @ApiModelProperty("订单状态: NEED_PAY-未支付 NEED_SEND_GOODS-待发货 NEED_RECEIVE_GOODS-待收货 NEED_EVALUATE-待评价 ALREADY_EVALUATE-已完成" +
            "NEED_USE-待使用  ALREADY_CANCEL-已取消")
    private OrderStatusEnum orderStatus;

    @ApiModelProperty("订单类型: NON-无活动,REDUCED-满减,INTEGRALS-积分,SECKILL-秒杀,SPRLL_GROUP-拼团")
    private ActivityTypeEnum activityTypeEnum;

    @Min(1)
    private Integer pageNo;
    @Min(1)
    private Integer pageSize;
}
