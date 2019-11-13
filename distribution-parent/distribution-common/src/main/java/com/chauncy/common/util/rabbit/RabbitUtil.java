package com.chauncy.common.util.rabbit;

import com.chauncy.common.constant.RabbitConstants;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhangrt
 * @Date 2019/8/14 15:44
 **/
@Configuration
@Component
public class RabbitUtil {


    private final RabbitAdmin rabbitAdmin;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitUtil(RabbitAdmin rabbitAdmin, RabbitTemplate rabbitTemplate){
        this.rabbitAdmin = rabbitAdmin;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     *发送消息到延迟队列
     * @param expiration 消息过期时间(单位毫秒)
     * @param messageContent 消息具体内容
     */
    public void sendDelayMessage(Integer expiration,Object messageContent) {

        // 添加延时队列
        rabbitTemplate.convertAndSend(RabbitConstants.ORDER_DEAD_EXCHANGE, RabbitConstants.ORDER_REDIRECT_KEY,messageContent , message -> {

            message.getMessageProperties().setDelay(expiration);
            return message;
        });

    }



    /**
     * 海关申报发送消息到延迟队列
     * @param expiration 消息过期时间(单位毫秒)
     * @param messageContent 消息具体内容
     */
    public void customSendDelayMessage(String expiration, Object messageContent) {

        // 添加延时队列
        rabbitTemplate.convertAndSend(RabbitConstants.CUSTOM_DECLARE_EXCHANGE,
                RabbitConstants.CUSTOM_DECLARE_ROUTING_KEY,
                messageContent, message -> {
            // TODO 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间

            message.getMessageProperties().setExpiration(expiration);
            return message;
        });
    }
}