package com.springboot.rabbitmq;

import com.springboot.rabbitmq.sender.HelloSender;
import com.springboot.repository.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqHelloTest {
 
    @Autowired
    private HelloSender helloSender;
 
//    @Test
//    public void hello() throws Exception {
//        helloSender.send(1);
//    }
//
//    @Test
//    public void oneToMany() throws Exception {
//        for (int i=0;i<100;i++){
//            helloSender.send(i);
//        }
//    }

    @Test
    public void user() throws Exception {
        helloSender.send(new UserEntity().builder().userName("bobo").password("12345").build());
    }

    @Test
    public void directExchangeQuence() throws Exception {
       helloSender.sendDirect();
    }

    @Test
    public void topicExchangeQuence() throws Exception {
        helloSender.sendTopic();
    }

    @Test
    public void fanoutExchangeQuence() throws Exception {
        helloSender.sendFanout();
    }


}