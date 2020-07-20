package com.springboot.rabbitmq.receiver;

import com.springboot.repository.entity.UserEntity;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
//@RabbitListener(queues = {"fanout.A", "fanout.B", "fanout.C"})
public class FanoutReceiver {
 
    @RabbitHandler
    public void processFanout(UserEntity userEntity) {
        System.out.println("FanoutReceiver  : " + userEntity.toString());
    }
 
}