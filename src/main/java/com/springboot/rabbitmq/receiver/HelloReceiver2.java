package com.springboot.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

@Component
//@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class HelloReceiver2 {
 
    @RabbitHandler
    public void process2(String hello) {
        System.out.println("Receiver2  : " + hello);
    }
 
}