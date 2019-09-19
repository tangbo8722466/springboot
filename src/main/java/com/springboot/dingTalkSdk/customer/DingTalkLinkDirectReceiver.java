package com.springboot.dingTalkSdk.customer;

import com.dingtalk.api.response.OapiRobotSendResponse;
import com.springboot.dingTalkSdk.biz.DingTalkBiz;
import com.springboot.dingTalkSdk.config.DingTalkDirectConfig;
import com.springboot.dingTalkSdk.vo.request.LinkRequestVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
//@RabbitListener(queues = {DingTalkDirectConfig.DING_TALK_LINK})
public class DingTalkLinkDirectReceiver {

    @Autowired
    DingTalkBiz dingTalkBiz;

    @Value("${spring.rabbitmq.dingTalk.link.url}")
    private String dingTalkLinkUrl;

    @RabbitHandler
    public void processDirect(LinkRequestVo requestVo) {
        OapiRobotSendResponse response = dingTalkBiz.sendLink(dingTalkLinkUrl, requestVo);
        if (!response.isSuccess()) {
            log.info("send ding link:" + JSONObject.fromObject(response).toString());
        }
    }
}