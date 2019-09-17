package com.springboot.dingTalkSdk.customer;

import com.dingtalk.api.response.OapiRobotSendResponse;
import com.springboot.dingTalkSdk.biz.DingTalkBiz;
import com.springboot.dingTalkSdk.config.DingTalkDirectConfig;
import com.springboot.dingTalkSdk.vo.request.TextRequestVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = {DingTalkDirectConfig.DING_TALK_TEXT})
public class DingTalkTextDirectReceiver {

    @Autowired
    DingTalkBiz dingTalkBiz;

    @Value("${spring.rabbitmq.dingTalk.text.url}")
    private String dingTalkTextUrl;

    @RabbitHandler
    public void processDirect(TextRequestVo requestVo) {
        OapiRobotSendResponse response = dingTalkBiz.sendText(dingTalkTextUrl, requestVo);
        if (!response.isSuccess()){
            log.info("send ding text:"+ JSONObject.fromObject(response).toString());
        }
    }
 
}