package com.chauncy.common.constant;

/**
 * @Author zhangrt
 * @Date 2019/7/22 13:10
 **/
public interface RabbitConstants {



    /**
     * routing key 名称
     * TODO 此处的 routingKey 很重要要,具体消息发送在该 routingKey 的
     */
     static final String CLOSE_ORDER_ROUTING_KEY = "order.delay.key";

    /**
     * 关闭订单队列
     */
     static final String CLOSE_ORDER_QUEUE = "close.order.queue";
    /**
     * 关闭订单交换机
     */
     static final String CLOSE_ORDER_EXCHANGE = "close.order.exchange";


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
     * 海关申报队列
     */
    static final String CUSTOM_DECLARE_QUEUE = "custom.declare.queue";
    /**
     * 海关申报交换机
     */
    static final String CUSTOM_DECLARE_EXCHANGE = "custom.declare.exchange";
    /**
     * 延迟交换机与队列的路由键
     */
    static final String CUSTOM_DECLARE_ROUTING_KEY = "custom.declare.key";

    /**
     * 海关申报转发队列
     */
    static final String CUSTOM_DECLARE_REDIRECT_QUEUE = "custom.declare.redirect.queue";
    /**
     * 海关申报转发交换机
     */
    static final String CUSTOM_DECLARE_REDIRECT_EXCHANGE = "custom.declare.redirect.exchange";
    /**
     * 海关申报转发死信的路由键
     */
    static final String CUSTOM_DECLARE_REDIRECT_ROUTING_KEY = "custom.declare.redirect.key";

    /**
     * 账目流水队列
     */
    static final String PLATFORM_GIVE_QUEUE = "platform.give.queue";
    /**
     * 账目流水交换机
     */
    static final String PLATFORM_GIVE_EXCHANGE = "platform.give.exchange";
    /**
     * 延迟交换机与队列的路由键
     */
    static final String PLATFORM_GIVE_ROUTING_KEY = "platform.give.key";


    /**
     * DLX，死信交换机
     */
    static final String ORDER_DEAD_EXCHANGE = "order.dead.exchange";


    /**
     * 订单转发队列
     */
    static final String ORDER_REDIRECT_QUEUE = "order.redirect.queue";

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
     * 售后订单延迟72小时
     */
    static final Integer AFTER_DELAY_TIME = 72*60*60*1000;

    /**
     * 开团后24小时没拼团成功取消拼团
     */
    static final Integer CLOSE_GROUP_TIME = 24*60*60*1000;

    /**
     * 半小时内付款没成功取消拼团资格
     */
    static final Integer DEL_MEMBER_TIME = 30*60*1000;




    /**
     * 拼团失败
     */
    static final String CLOSE_GROUP_QUEUE = "close.group.queue";
    /**
     * 关闭订单交换机
     */
    static final String CLOSE_GROUP_EXCHANGE = "close.group.exchange";
    /**
     * 延迟交换机与队列的路由键
     */
    static final String CLOSE_GROUP_ROUTING_KEY = "all";










    /**
     * 参团失败
     */
    static final String DEL_MEMBER_QUEUE = "del.member.queue";
    /**
     *
     */
    static final String DEL_MEMBER_EXCHANGE = "del.member.exchange";
    /**
     * 延迟交换机与队列的路由键
     */
    static final String DEL_MEMBER_ROUTING_KEY = "all";


}
