package com.chauncy.common.constant;

/**
 * @Author zhangrt
 * @Date 2019/7/22 13:10
 **/
public interface RabbitConstants {

    /**
     * 延迟队列 TTL 名称
     */
     static final String ORDER_DELAY_QUEUE = "order.delay.queue";
    /**
     * DLX，dead letter发送到的 exchange
     * TODO 此处的 exchange 很重要,具体消息就是发送到该交换机的
     */
     static final String REGISTER_DELAY_EXCHANGE = "order.delay.exchange";
    /**
     * routing key 名称
     * TODO 此处的 routingKey 很重要要,具体消息发送在该 routingKey 的
     */
     static final String DELAY_ROUTING_KEY = "order.delay.key";

    /**
     * 提交订单队列
     */
     static final String SUBMIT_ORDER_QUEUE = "submit.order.queue";
    /**
     * 提交订单交换机
     */
     static final String SUBMIT_ORDER_EXCHANGE = "submit.order.exchange";
     static final String ROUTING_KEY = "all";
}
