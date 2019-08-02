package com.chauncy.web.config.rabbit.handler;

import com.chauncy.common.constant.RabbitConstants;
import com.chauncy.common.enums.app.order.PayOrderStatusEnum;
import com.chauncy.common.util.LoggerUtil;
import com.chauncy.data.domain.po.pay.PayOrderPo;
import com.chauncy.order.service.IOmOrderService;
import com.chauncy.order.service.IPayOrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Author zhangrt
 * @Date 2019/7/22 15:34
 **/

@Component
public class AutoCloseOrderHandler {

    @Autowired
    private IPayOrderService payOrderService;

    @Autowired
    private IOmOrderService orderService;

    @RabbitListener(queues = {RabbitConstants.SUBMIT_ORDER_QUEUE})
    public void listenerDelayQueue(Long payOrderId, Message message, Channel channel) {
        LoggerUtil.info(String.format("[closeOrderByPayId 监听的消息] - [消费时间] - [%s] - [%s]", LocalDateTime.now(), payOrderId));
        PayOrderPo queryPayOrder = payOrderService.getById(payOrderId);
        //如果订单状态为未支付,就去取消订单
        if (queryPayOrder.getStatus().equals(PayOrderStatusEnum.NEED_PAY.getId())){
            orderService.closeOrderByPayId(payOrderId);
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
// TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
        }
    }
}
