package com.springboot.rabbitmq.receiver;

import com.springboot.repository.entity.UserEntity;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
//@RabbitListener(queues = "object")
public class EntityReceiver {
 
    @RabbitHandler
    public void processEntity(UserEntity userEntity) {
        System.out.println("EntityReceiver  : " + userEntity.toString());
    }
 
}