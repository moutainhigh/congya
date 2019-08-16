package com.chauncy.common.constant;

/**
 * @Author zhangrt
 * @Date 2019/7/22 13:10
 **/
public interface RabbitConstants {

    /**
     * 延迟待付款队列名称
     */
     static final String ORDER_UNPAID_DELAY_QUEUE = "order.unpaid.delay.queue";
    /**
     * DLX，dead letter发送到的 exchange
     * TODO 此处的 exchange 很重要,具体消息就是发送到该交换机的
     */
     static final String ORDER_UNPAID_DELAY_EXCHANGE = "order.delay.exchange";
    /**
     * routing key 名称
     * TODO 此处的 routingKey 很重要要,具体消息发送在该 routingKey 的
     */
     static final String DELAY_ROUTING_KEY = "order.delay.key";

    /**
     * 关闭订单队列
     */
     static final String CLOSE_ORDER_QUEUE = "close.order.queue";
    /**
     * 关闭订单交换机
     */
     static final String CLOSE_ORDER_EXCHANGE = "close.order.exchange";
    /**
     * 延迟交换机与队列的路由键
     */
    static final String ROUTING_KEY = "all";


    /**
     * 延迟待评价队列名称
     */
    static final String ORDER_UNCOMMENT_DELAY_QUEUE = "order.uncomment.delay.queue";
    /**
     * DLX，dead letter发送到的 exchange
     * TODO 此处的 exchange 很重要,具体消息就是发送到该交换机的
     */
    static final String ORDER_UNCOMMENT_DELAY_EXCHANGE = "order.uncomment.delay.exchange";
    /**
     * routing key 名称
     * TODO 此处的 routingKey 很重要要,具体消息发送在该 routingKey 的
     */
    static final String ORDER_UNCOMMENT_DELAY_ROUTING_KEY = "order.uncomment.delay.key";

    /**
     * 自动评价队列
     */
    static final String AUTO_COMMENT_QUEUE = "auto.comment.queue";
    /**
     * 自动评价交换机
     */
    static final String AUTO_COMMENT_EXCHANGE = "auto.comment.exchange";
    /**
     * 自动评价routingkey
     */
    static final String AUTO_COMMENT_ROUTING_KEY = "all";
}
