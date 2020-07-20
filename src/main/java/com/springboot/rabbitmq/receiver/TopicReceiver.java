package com.springboot.rabbitmq.receiver;

import com.springboot.rabbitmq.config.TopicRabbitConfig;
import com.springboot.repository.entity.UserEntity;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
//@RabbitListener(queues = {"topic.message", "topic.messages"})
public class TopicReceiver {
 
    @RabbitHandler
    public void processTopic(UserEntity userEntity) {
        System.out.println("TopicReceiver  : " + userEntity.toString());
    }
 
}