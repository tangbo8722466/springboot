package com.springboot.dingTalkSdk.customer;

import com.dingtalk.api.response.OapiRobotSendResponse;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import com.springboot.dingTalkSdk.biz.DingTalkBiz;
import com.springboot.dingTalkSdk.config.DingTalkDirectConfig;
import com.springboot.dingTalkSdk.config.RabbitConfig;
import com.springboot.dingTalkSdk.vo.request.TextRequestVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
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
    public void processDirect(@Payload TextRequestVo requestVo, com.rabbitmq.client.Channel channel,@Headers Map<String,Object> map, Message message) {
        log.info("消费端接收到消息:" + message.getMessageProperties());
        log.info("请求参数:" + new String(message.getBody()));
        log.info("队列topic:" + message.getMessageProperties().getReceivedRoutingKey());
        log.info("消息DeliveryTag:" + message.getMessageProperties().getDeliveryTag());
        try {
            OapiRobotSendResponse response = dingTalkBiz.sendText(dingTalkTextUrl, requestVo);
            log.info("请求响应:" + com.alibaba.fastjson.JSONObject.toJSONString(response));
            if (!response.isSuccess()) {
                //消费失败重试
                if (RabbitConfig.getRetryCount(message.getMessageProperties()) <= 3) {
                    dingTalkBiz.sendTextIntoDelayQueue(message, requestVo);
                }
            }
            //deliveryTag:该消息的index
            //multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。
            // false只确认当前一个消息收到，true确认所有consumer获得的消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (message.getMessageProperties().getRedelivered() || (RabbitConfig.getRetryCount(message.getMessageProperties()) <= 3)) {
                    log.info("消息已重复处理失败或已重试3次, 拒绝再次接收...");
                    //deliveryTag:该消息的index
                    //requeue：被拒绝的是否重新入队列
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                } else {
                    log.info("消息即将再次返回队列处理...");
                    //deliveryTag:该消息的index
                    //multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
                    //requeue：被拒绝的是否重新入队列
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        log.info("消费端消费结束");
    }
}