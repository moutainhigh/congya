package com.chauncy.data.bo.app.order.rabbit;

import com.chauncy.common.enums.app.order.OrderStatusEnum;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 订单消息队列传递的消息格式
 * @Author zhangrt
 * @Date 2019/8/20 0:11
 **/
@Data
@Accessors(chain = true)
@ToString
public class RabbitOrderBo {

    private Long orderId;

    private OrderStatusEnum orderStatusEnum;
}
