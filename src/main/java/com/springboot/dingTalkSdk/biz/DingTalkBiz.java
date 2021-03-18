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
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

@Service
@Component
@Slf4j
public class DingTalkBiz implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    @Autowired
    RabbitTemplate rabbitTemplate;

    //PostConstruct注释用于在完成依赖注入以执行任何初始化之后需要执行的方法
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);            //指定 ConfirmCallback
        rabbitTemplate.setReturnCallback(this);             //指定 ReturnCallback
    }

    public RestResult<Empty> sendTextIntoQueue(TextRequestVo requestVo){
        try {
            CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
            log.info("Text消费发送id:" + correlationId.getId());
            //源码内部，默认设置消息为持久化
            /**
             * public MessageProperties() {
             *         this.deliveryMode = DEFAULT_DELIVERY_MODE;
             *         this.priority = DEFAULT_PRIORITY;
             *     }
             */
            rabbitTemplate.convertAndSend(DingTalkDirectConfig.DING_TALK_EXCHANGE, DingTalkDirectConfig.DING_TALK_TEXT, requestVo, correlationId);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return RestResult.buildFailResponse(ex.getMessage());
        }

        return RestResult.buildSuccessResponse();
    }

    public RestResult<Empty> sendTextIntoDelayQueue(Message originMessage, TextRequestVo requestVo){
        try {
            CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
            log.info("Text延迟消费发送id:" + correlationId.getId());
            long expiration = 5000;
            MessagePostProcessor processor = new MessagePostProcessor(){
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message = originMessage;
                    message.getMessageProperties().setExpiration(String.valueOf(expiration));
                    return message;
                }
            };
            rabbitTemplate.convertAndSend(DingTalkDirectConfig.DING_TALK_EXCHANGE, DingTalkDirectConfig.DING_TALK_DELAY_TEXT, requestVo, processor, correlationId);
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
        }
        catch(Exception ex){
            ex.printStackTrace();
            return RestResult.buildFailResponse(ex.getMessage());
        }
        return RestResult.buildSuccessResponse();
    }

    public  RestResult<Empty> sendLinkIntoDelayQueue(LinkRequestVo requestVo){
        try {
            CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
            log.info("Link延迟消费发送id:" + correlationId);
            long expiration = 5000;
            MessagePostProcessor processor = new MessagePostProcessor(){
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setExpiration(String.valueOf(expiration));
                    return message;
                }
            };
            rabbitTemplate.convertAndSend(DingTalkDirectConfig.DING_TALK_EXCHANGE, DingTalkDirectConfig.DING_TALK_DELAY_LINK, requestVo, processor, correlationId);
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
        }
        catch(Exception ex){
            ex.printStackTrace();
            return RestResult.buildFailResponse(ex.getMessage());
        }
        return RestResult.buildSuccessResponse();
    }

    public  RestResult<Empty> sendMarkdownIntoDelayQueue(MarkDownRequestVo requestVo){
        try {
            CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
            log.info("MarkDown延迟消费发送id:" + correlationId);
            long expiration = 5000;
            MessagePostProcessor processor = new MessagePostProcessor(){
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setExpiration(String.valueOf(expiration));
                    return message;
                }
            };
            rabbitTemplate.convertAndSend(DingTalkDirectConfig.DING_TALK_EXCHANGE, DingTalkDirectConfig.DING_TALK_DELAY_MARKDOWN, requestVo, processor, correlationId);
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
        log.info("回调id:" + correlationData.getId());
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
