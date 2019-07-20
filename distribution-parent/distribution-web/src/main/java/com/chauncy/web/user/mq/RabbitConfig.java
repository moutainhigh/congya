package com.chauncy.web.user.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author cheng
 * @create 2019-07-19 16:01
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue helloQueue() {
        return new Queue("a");
    }

}
