package com.springboot.rabbitmq.receiver;

import com.springboot.repository.entity.UserEntity;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"apple", "pear"})
public class DirectReceiver {
 
    @RabbitHandler
    public void processDirect(String message) {
        System.out.println("DirectReceiver  : " + message);
    }
 
}