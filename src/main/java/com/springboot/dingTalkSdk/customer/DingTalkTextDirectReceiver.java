package com.springboot.dingTalkSdk.customer;

import com.dingtalk.api.response.OapiRobotSendResponse;
import com.rabbitmq.client.impl.AMQImpl;
import com.springboot.dingTalkSdk.biz.DingTalkBiz;
import com.springboot.dingTalkSdk.config.DingTalkDirectConfig;
import com.springboot.dingTalkSdk.vo.request.TextRequestVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
@RabbitListener(queues = {DingTalkDirectConfig.DING_TALK_TEXT})
public class DingTalkTextDirectReceiver {

    @Autowired
    DingTalkBiz dingTalkBiz;

    @Value("${spring.rabbitmq.dingTalk.text.url}")
    private String dingTalkTextUrl;

    @RabbitHandler
    public void processDirect(TextRequestVo requestVo, com.rabbitmq.client.Channel channel,@Headers Map<String,Object> map) {
        OapiRobotSendResponse response = dingTalkBiz.sendText(dingTalkTextUrl, requestVo);
        if (!response.isSuccess()){
            log.info("send ding text:"+ JSONObject.fromObject(response).toString());
            try {
                channel.basicNack((Long)map.get(AmqpHeaders.DELIVERY_TAG),false,true);      //否认消息
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            channel.basicAck((Long)map.get(AmqpHeaders.DELIVERY_TAG),false);            //确认消息
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}