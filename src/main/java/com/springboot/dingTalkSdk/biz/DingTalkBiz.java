package com.springboot.dingTalkSdk.biz;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.springboot.Utils.RestResult;
import com.springboot.Vo.response.Empty;
import com.springboot.dingTalkSdk.config.DingTalkDirectConfig;
import com.springboot.dingTalkSdk.vo.request.LinkRequestVo;
import com.springboot.dingTalkSdk.vo.request.MarkDownRequestVo;
import com.springboot.dingTalkSdk.vo.request.TextRequestVo;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class DingTalkBiz implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    @Autowired
    RabbitTemplate rabbitTemplate;


    public RestResult<Empty> sendTextIntoQueue(TextRequestVo requestVo){
        try {
            CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
            log.info("Text消费发送id:" + correlationId);
            rabbitTemplate.convertAndSend(DingTalkDirectConfig.DING_TALK_EXCHANGE, DingTalkDirectConfig.DING_TALK_TEXT, requestVo, correlationId);
            Thread.sleep(1000);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return RestResult.buildFailResponse(ex.getMessage());
        }

        return RestResult.buildSuccessResponse();
    }

    public  RestResult<Empty> sendLinkIntoQueue(LinkRequestVo requestVo){
        try {
            CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
            log.info("Link消费发送id:" + correlationId);
            rabbitTemplate.convertAndSend(DingTalkDirectConfig.DING_TALK_EXCHANGE, DingTalkDirectConfig.DING_TALK_LINK, requestVo, correlationId);
            Thread.sleep(1000);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return RestResult.buildFailResponse(ex.getMessage());
        }
        return RestResult.buildSuccessResponse();
    }

    public  RestResult<Empty> sendMarkdownIntoQueue(MarkDownRequestVo requestVo){
        try {
            CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
            log.info("MarkDown消费发送id:" + correlationId);
            rabbitTemplate.convertAndSend(DingTalkDirectConfig.DING_TALK_EXCHANGE, DingTalkDirectConfig.DING_TALK_MARKDOWN, requestVo, correlationId);
            Thread.sleep(1000);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return RestResult.buildFailResponse(ex.getMessage());
        }
        return RestResult.buildSuccessResponse();
    }

    public OapiRobotSendResponse sendText(String serverUrl, TextRequestVo requestVo){
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(requestVo.getContent());
        request.setText(text);

        return excute(serverUrl, request, requestVo.getAtUsers(), requestVo.getIsAtAll());
    }

    public OapiRobotSendResponse sendLink(String serverUrl, LinkRequestVo requestVo){
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("link");
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setMessageUrl(requestVo.getMessageUrl());
        link.setPicUrl(requestVo.getPicUrl());
        link.setTitle(requestVo.getTitle());
        link.setText(requestVo.getContent());
        request.setLink(link);

        return excute(serverUrl, request, requestVo.getAtUsers(), requestVo.getIsAtAll());
    }

    public OapiRobotSendResponse sendMarkdown(String serverUrl, MarkDownRequestVo requestVo){
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(requestVo.getTitle());
        markdown.setText(requestVo.getContent());
        request.setMarkdown(markdown);

        return excute(serverUrl, request, requestVo.getAtUsers(), requestVo.getIsAtAll());
    }

    public OapiRobotSendResponse excute(String serverUrl,  OapiRobotSendRequest request, List<String> AtUsers, Boolean isAtAll){
        DingTalkClient client = new DefaultDingTalkClient(serverUrl);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        if (!CollectionUtils.isEmpty(AtUsers)) {
            at.setAtMobiles(AtUsers);
        }
        at.setIsAtAll(isAtAll.toString());
        request.setAt(at);
        try {
            OapiRobotSendResponse response = client.execute(request);
            return response;
        } catch (ApiException e) {
            e.printStackTrace();
            OapiRobotSendResponse response = new OapiRobotSendResponse();
            response.setErrcode(500L);
            response.setErrmsg(e.getErrMsg());
            return response;
        }
    }

    /**
        callback是异步的,所以测试中sleep1秒钟等待下)
        总结下就是:
        如果消息没有到exchange,则confirm回调,ack=false
        如果消息到达exchange,则confirm回调,ack=true
        exchange到queue成功,则不回调return
        exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
    */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("回调id:" + correlationData);
        if (ack) {
            log.info("消息成功消费");
        } else {
            log.info("消息消费失败:" + cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("return--message:"+new String(message.getBody())+",replyCode:"+replyCode+",replyText:"+replyText
                +",exchange:"+exchange+",routingKey:"+routingKey);
    }
}
