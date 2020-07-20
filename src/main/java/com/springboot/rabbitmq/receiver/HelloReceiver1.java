package com.springboot.rabbitmq.receiver;

import com.springboot.rabbitmq.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
//@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class HelloReceiver1 {
 
    @RabbitHandler
    public void process1(String hello) {
        System.out.println("Receiver1  : " + hello);
    }
}