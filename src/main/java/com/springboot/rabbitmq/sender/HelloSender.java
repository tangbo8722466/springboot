package com.springboot.rabbitmq.sender;

import com.springboot.rabbitmq.config.RabbitConfig;
import com.springboot.repository.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HelloSender {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hello " + new Date();
       logger.info("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }

    public void send(int i) {
        String context = "message id:"+i+"," + new Date();
        logger.info("Sender : " + context);
        this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_A, context);
    }

    //发送者
    public void send(UserEntity user) {
        logger.info("Sender object: " + user.toString());
        this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_B, user);
//        callBackSender.send(RabbitConfig.QUEUE_B, user);
    }

    public void sendDirect() {
        String context = "hi, direct message";
        logger.info("sendDirect : " + context);
        this.rabbitTemplate.convertAndSend("directExchange", "apple", context);
        this.rabbitTemplate.convertAndSend("directExchange", "apple", context);
        this.rabbitTemplate.convertAndSend("directExchange", "pear", context);
        this.rabbitTemplate.convertAndSend("directExchange", "test", context);
//        callBackSender.send("directExchange", "apple", context);
//        callBackSender.send("directExchange", "apple", context);
//        callBackSender.send("directExchange", "pear", context);
//        callBackSender.send("directExchange", "test", context);

    }

    public void sendTopic() {
        String context = "hi, i am message 1";
        logger.info("sendTopic : " + context);
        this.rabbitTemplate.convertAndSend("topicExchange", "topic.message", context);
        this.rabbitTemplate.convertAndSend("topicExchange", "topic.messages", context);
//        callBackSender.send("topicExchange", "topic.message", context);
//        callBackSender.send("topicExchange", "topic.messages", context);

    }

    public void sendFanout() {
        String context = "hi, fanout msg ";
        logger.info("Sender : " + context);
        this.rabbitTemplate.convertAndSend("fanoutExchange","", context);
//        callBackSender.send("fanoutExchange","", context);
    }




}