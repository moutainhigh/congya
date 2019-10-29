package com.chauncy.web.config.rabbit.config;

import com.chauncy.common.constant.RabbitConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhangrt
 * @Date 2019/7/21 22:24
 **/
@Configuration
public class RabbitConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitConfig.class);

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //json序列化
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        // 消息发送失败返回到队列中, yml需要配置 publisher-returns: true
        rabbitTemplate.setMandatory(true);

        // 消息返回, yml需要配置 publisher-returns: true
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });

        // 消息确认, yml需要配置 publisher-confirms: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                // log.debug("消息发送到exchange成功,id: {}", correlationData.getId());
            } else {
                log.debug("消息发送到exchange失败,原因: {}", cause);
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return  rabbitAdmin;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }



    /**
     * 延迟队列配置
     * <p>
     * 1、params.put("x-message-ttl", 5 * 1000);
     * TODO 第一种方式是直接设置 Queue 延迟时间 但如果直接给队列设置过期时间,这种做法不是很灵活,（当然二者是兼容的,默认是时间小的优先）
     * 2、rabbitTemplate.convertAndSend(book, message -> {
     * message.getMessageProperties().setExpiration(2 * 1000 + "");
     * return message;
     * });
     * TODO 第二种就是每次发送消息动态设置延迟时间,这样我们可以灵活控制
     **/
    @Bean
    public Queue delayOrderQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", RabbitConstants.CLOSE_ORDER_EXCHANGE);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", RabbitConstants.ROUTING_KEY);
        return new Queue(RabbitConstants.ORDER_UNPAID_DELAY_QUEUE, true, false, false, params);
    }

    /**
     * 需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。
     * 这是一个完整的匹配。如果一个队列绑定到该交换机上要求路由键 “dog”，则只有被标记为“dog”的消息才被转发，不会转发dog.puppy，也不会转发dog.guard，只会转发dog。
     * TODO 它不像 TopicExchange 那样可以使用通配符适配多个
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange delayOrderExchange() {
        return new DirectExchange(RabbitConstants.ORDER_UNPAID_DELAY_EXCHANGE);
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(delayOrderQueue()).to(delayOrderExchange()).with(RabbitConstants.DELAY_ROUTING_KEY);
    }


    /**
     * 账目流水 消息队列
     * @return
     */
    @Bean
    public Queue accountLogQueue() {
        return new Queue(RabbitConstants.ACCOUNT_LOG_QUEUE, true);
    }

    /**
     * 账目流水 消息交换机
     **/
    @Bean
    public TopicExchange accountLogTopicExchange() {
        return new TopicExchange(RabbitConstants.ACCOUNT_LOG_EXCHANGE);
    }

    @Bean
    public Binding accountLogBinding() {
        // TODO 如果要让延迟队列之间有关联,这里的 routingKey 和 绑定的交换机很关键
        return BindingBuilder.bind(accountLogQueue()).to(accountLogTopicExchange()).with(RabbitConstants.ACCOUNT_LOG_ROUTING_KEY);
    }

    /**
     * 海关申报 消息队列  延迟队列
     * @return
     */
    @Bean
    public Queue customDeclareQueue() {
        //死信队列
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", RabbitConstants.CUSTOM_DECLARE_REDIRECT_EXCHANGE);
        params.put("x-dead-letter-routing-key", RabbitConstants.CUSTOM_DECLARE_REDIRECT_ROUTING_KEY);
        return new Queue(RabbitConstants.CUSTOM_DECLARE_QUEUE, true, false,false, params);
    }

    /**
     * 海关申报 消息交换机
     **/
    @Bean
    public DirectExchange customDeclareExchange() {
        return new DirectExchange(RabbitConstants.CUSTOM_DECLARE_EXCHANGE);
    }

    @Bean
    public Binding customDeclareBinding() {
        // TODO 如果要让延迟队列之间有关联,这里的 routingKey 和 绑定的交换机很关键
        return BindingBuilder.bind(customDeclareQueue()).to(customDeclareExchange())
                .with(RabbitConstants.CUSTOM_DECLARE_ROUTING_KEY);
    }

    /**
     * 海关申报转发队列
     * @return
     */
    @Bean
    public Queue customDeclareRedirectQueue() {
        return new Queue(RabbitConstants.CUSTOM_DECLARE_REDIRECT_QUEUE, true);
    }

    /**
     * 海关申报死信转发的交换机
     * @return
     */
    @Bean
    public DirectExchange customDeclareRedirectExchange() {
        return new DirectExchange(RabbitConstants.CUSTOM_DECLARE_REDIRECT_EXCHANGE);
    }


    /**
     * 海关申报死信交换机与队列绑定
     * @return
     */
    @Bean
    public Binding customDeclareRedirectBinding() {
        // TODO 如果要让延迟队列之间有关联,这里的 routingKey 和 绑定的交换机很关键
        return BindingBuilder.bind(customDeclareRedirectQueue()).
                to(customDeclareRedirectExchange()).with(RabbitConstants.CUSTOM_DECLARE_REDIRECT_ROUTING_KEY);
    }




    /**
     * 系统赠送 消息队列
     * @return
     */
    @Bean
    public Queue platformGiveQueue() {
        return new Queue(RabbitConstants.PLATFORM_GIVE_QUEUE, true);
    }

    /**
     * 账目流水 消息交换机
     **/
    @Bean
    public TopicExchange platformGiveTopicExchange() {
        return new TopicExchange(RabbitConstants.PLATFORM_GIVE_EXCHANGE);
    }

    @Bean
    public Binding platformGiveBinding() {
        // TODO 如果要让延迟队列之间有关联,这里的 routingKey 和 绑定的交换机很关键
        return BindingBuilder.bind(platformGiveQueue()).to(platformGiveTopicExchange()).with(RabbitConstants.PLATFORM_GIVE_ROUTING_KEY);
    }


    @Bean
    public Queue submitOrderQueue() {
        return new Queue(RabbitConstants.CLOSE_ORDER_QUEUE, true);
    }

    /**
     * 将路由键和某模式进行匹配。此时队列需要绑定要一个模式上。
     * 符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词。因此“audit.#”能够匹配到“audit.irs.corporate”，但是“audit.*” 只会匹配到“audit.irs”。
     **/
    @Bean
    public TopicExchange submitOrderTopicExchange() {
        return new TopicExchange(RabbitConstants.CLOSE_ORDER_EXCHANGE);
    }

    @Bean
    public Binding submitOrderBinding() {
        // TODO 如果要让延迟队列之间有关联,这里的 routingKey 和 绑定的交换机很关键
        return BindingBuilder.bind(submitOrderQueue()).to(submitOrderTopicExchange()).with(RabbitConstants.ROUTING_KEY);
    }

    @Bean
    public Queue orderDeadQueue() {
        //死信队列
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", RabbitConstants.ORDER_REDIRECT_EXCHANGE);
        params.put("x-dead-letter-routing-key", RabbitConstants.ORDER_REDIRECT_KEY);
        return new Queue(RabbitConstants.ORDER_DEAD_QUEUE, true, false, false, params);
    }

    @Bean
    public DirectExchange orderDeadExchange() {
        //死信交换机
        return new DirectExchange(RabbitConstants.ORDER_DEAD_EXCHANGE);
    }

    @Bean
    public Binding dlxOrderBinding() {
        return BindingBuilder.bind(orderDeadQueue()).to(orderDeadExchange()).with(RabbitConstants.ORDER_DEAD_ROUTING_KEY);
    }


    /**
     * 转发队列
     * @return
     */
    @Bean
    public Queue redirectOrderQueue() {
        return new Queue(RabbitConstants.ORDER_REDIRECT_QUEUE, true);
    }

    /**
     * 死信转发的交换机
     * @return
     */
    @Bean
    public TopicExchange orderRedirectExchange() {
        return new TopicExchange(RabbitConstants.ORDER_REDIRECT_EXCHANGE);
    }

    /**
     * 死信交换机与队列绑定
     * @return
     */
    @Bean
    public Binding orderRedirectBinding() {
        // TODO 如果要让延迟队列之间有关联,这里的 routingKey 和 绑定的交换机很关键
        return BindingBuilder.bind(redirectOrderQueue()).
                to(orderRedirectExchange()).with(RabbitConstants.ORDER_REDIRECT_KEY);
    }



    @Bean
    public Queue afterDeadQueue() {
        //死信队列
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", RabbitConstants.AFTER_REDIRECT_EXCHANGE);
        params.put("x-dead-letter-routing-key", RabbitConstants.AFTER_REDIRECT_KEY);
        return new Queue(RabbitConstants.AFTER_DEAD_QUEUE, true, false, false, params);
    }

    @Bean
    public DirectExchange afterDeadExchange() {
        //死信交换机
        return new DirectExchange(RabbitConstants.AFTER_DEAD_EXCHANGE);
    }

    @Bean
    public Binding dlxAfterBinding() {
        return BindingBuilder.bind(afterDeadQueue()).to(afterDeadExchange()).with(RabbitConstants.AFTER_DEAD_ROUTING_KEY);
    }


    /**
     * 转发队列
     * @return
     */
    @Bean
    public Queue redirectAfterQueue() {
        return new Queue(RabbitConstants.AFTER_REDIRECT_QUEUE, true);
    }

    /**
     * 死信转发的交换机
     * @return
     */
    @Bean
    public TopicExchange afterRedirectExchange() {
        return new TopicExchange(RabbitConstants.AFTER_REDIRECT_EXCHANGE);
    }

    /**
     * 死信交换机与队列绑定
     * @return
     */
    @Bean
    public Binding afterRedirectBinding() {
        // TODO 如果要让延迟队列之间有关联,这里的 routingKey 和 绑定的交换机很关键
        return BindingBuilder.bind(redirectAfterQueue()).
                to(afterRedirectExchange()).with(RabbitConstants.AFTER_REDIRECT_KEY);
    }



    @Bean
    public Queue openGroupQueue() {
        //死信队列
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", RabbitConstants.CLOSE_GROUP_EXCHANGE);
        params.put("x-dead-letter-routing-key", RabbitConstants.CLOSE_GROUP_ROUTING_KEY);
        return new Queue(RabbitConstants.OPEN_GROUP_DELAY_QUEUE, true, false, false, params);
    }

    @Bean
    public DirectExchange openGroupExchange() {
        //死信交换机
        return new DirectExchange(RabbitConstants.OPEN_GROUP_DELAY_EXCHANGE);
    }

    @Bean
    public Binding dlxOpenGroupBinding() {
        return BindingBuilder.bind(openGroupQueue()).to(openGroupExchange()).with(RabbitConstants.OPEN_GROUP_DELAY_ROUTING_KEY);
    }


    /**
     * 转发队列
     * @return
     */
    @Bean
    public Queue closeGroupQueue() {
        return new Queue(RabbitConstants.CLOSE_GROUP_QUEUE, true);
    }

    /**
     * 死信转发的交换机
     * @return
     */
    @Bean
    public TopicExchange closeGroupExchange() {
        return new TopicExchange(RabbitConstants.CLOSE_GROUP_EXCHANGE);
    }

    /**
     * 死信交换机与队列绑定
     * @return
     */
    @Bean
    public Binding closeGroupBinding() {
        return BindingBuilder.bind(closeGroupQueue()).
                to(closeGroupExchange()).with(RabbitConstants.CLOSE_GROUP_ROUTING_KEY);
    }



    @Bean
    public Queue addMemberQueue() {
        //死信队列
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", RabbitConstants.DEL_MEMBER_EXCHANGE);
        params.put("x-dead-letter-routing-key", RabbitConstants.DEL_MEMBER_ROUTING_KEY);
        return new Queue(RabbitConstants.ADD_MEMBER_DELAY_QUEUE, true, false, false, params);
    }

    @Bean
    public DirectExchange addMemberExchange() {
        //死信交换机
        return new DirectExchange(RabbitConstants.ADD_MEMBER__DELAY_EXCHANGE);
    }

    @Bean
    public Binding dlxAddMemberBinding() {
        return BindingBuilder.bind(addMemberQueue()).to(addMemberExchange()).with(RabbitConstants.ADD_MEMBER_DELAY_ROUTING_KEY);
    }


    /**
     * 转发队列
     * @return
     */
    @Bean
    public Queue delMemberQueue() {
        return new Queue(RabbitConstants.DEL_MEMBER_QUEUE, true);
    }

    /**
     * 死信转发的交换机
     * @return
     */
    @Bean
    public TopicExchange delMemberExchange() {
        return new TopicExchange(RabbitConstants.DEL_MEMBER_EXCHANGE);
    }

    /**
     * 死信交换机与队列绑定
     * @return
     */
    @Bean
    public Binding delMemberBinding() {
        return BindingBuilder.bind(delMemberQueue()).
                to(delMemberExchange()).with(RabbitConstants.DEL_MEMBER_ROUTING_KEY);
    }



}