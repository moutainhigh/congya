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
     * 账目流水队列
     */
    static final String ACCOUNT_LOG_QUEUE = "account.log.queue";
    /**
     * 账目流水交换机
     */
    static final String ACCOUNT_LOG_EXCHANGE = "account.log.exchange";
    /**
     * 延迟交换机与队列的路由键
     */
    static final String ACCOUNT_LOG_ROUTING_KEY = "account.log.key";

    /**
     * 死信队列名称
     */
    static final String ORDER_DEAD_QUEUE = "order.dead.queue";
    /**
     * DLX，死信交换机
     */
    static final String ORDER_DEAD_EXCHANGE = "order.dead.exchange";
    /**
     * 死信交换机与队列的routing key 名称
     */
    static final String ORDER_DEAD_ROUTING_KEY = "order.dead.key";

    /**
     * 订单转发队列
     */
    static final String ORDER_REDIRECT_QUEUE = "order.redirect.queue";
    /**
     * 订单转发交换机
     */
    static final String ORDER_REDIRECT_EXCHANGE = "order.redirect.exchange";
    /**
     * 订单转发死信的路由键
     */
    static final String ORDER_REDIRECT_KEY = "all";



    /**
     * 售后死信队列名称
     */
    static final String AFTER_DEAD_QUEUE = "after.dead.queue";
    /**
     * DLX，死信交换机
     */
    static final String AFTER_DEAD_EXCHANGE = "after.dead.exchange";
    /**
     * 死信交换机与队列的routing key 名称
     */
    static final String AFTER_DEAD_ROUTING_KEY = "after.dead.key";

    /**
     * 订单转发队列
     */
    static final String AFTER_REDIRECT_QUEUE = "after.redirect.queue";
    /**
     * 订单转发交换机
     */
    static final String AFTER_REDIRECT_EXCHANGE = "after.redirect.exchange";
    /**
     * 订单转发死信的路由键
     */
    static final String AFTER_REDIRECT_KEY = "after.redirect.key";


    /**
     * 售后订单延迟72小时
     */
    static final String AFTER_DELAY_TIME = 72*60*60*1000+"";


}
