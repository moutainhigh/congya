package com.chauncy.web.user.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author cheng
 * @create 2019-08-07 20:20
 */
/*
@Component
@RabbitListener(queues = "chauncy_test")
public class Receiver {

    @RabbitHandler
    public void process(String a) {
        System.out.println("Receiver : " + a);
    }

}
*/
