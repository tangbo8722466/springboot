package com.springboot.dingTalkSdk.customer;

import com.dingtalk.api.response.OapiRobotSendResponse;
import com.springboot.dingTalkSdk.biz.DingTalkBiz;
import com.springboot.dingTalkSdk.config.DingTalkDirectConfig;
import com.springboot.dingTalkSdk.vo.request.MarkDownRequestVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = {DingTalkDirectConfig.DING_TALK_MARKDOWN})
public class DingTalkMarkDownDirectReceiver {

    @Autowired
    DingTalkBiz dingTalkBiz;

   @Value("${spring.rabbitmq.dingTalk.markdown.url}")
   private String dingTalkMarkdownUrl;
 
    @RabbitHandler
    public void processDirect(MarkDownRequestVo requestVo) {
        OapiRobotSendResponse response = dingTalkBiz.sendMarkdown(dingTalkMarkdownUrl, requestVo);
        if (!response.isSuccess()) {
            log.info("send ding markdown:" + JSONObject.fromObject(response).toString());
        }
    }
}