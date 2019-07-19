package com.chauncy.web.user.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author cheng
 * @create 2019-07-19 16:01
 */
@Component
@RabbitListener(queues = "a")
public class Receiver {

    @RabbitHandler
    public void process(String a) {
        System.out.println("Receiver : " + a);
    }

}
