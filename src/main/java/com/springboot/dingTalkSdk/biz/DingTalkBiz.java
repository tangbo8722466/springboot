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
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class DingTalkBiz {

    @Autowired
    AmqpTemplate amqpTemplate;

    public RestResult<Empty> sendTextIntoQueue(TextRequestVo requestVo){
        try {
            amqpTemplate.convertAndSend(DingTalkDirectConfig.DING_TALK_EXCHANGE, DingTalkDirectConfig.DING_TALK_TEXT, requestVo);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return RestResult.buildFailResponse(ex.getMessage());
        }
        return RestResult.buildSuccessResponse();
    }

    public  RestResult<Empty> sendLinkIntoQueue(LinkRequestVo requestVo){
        try {
            amqpTemplate.convertAndSend(DingTalkDirectConfig.DING_TALK_EXCHANGE, DingTalkDirectConfig.DING_TALK_LINK, requestVo);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return RestResult.buildFailResponse(ex.getMessage());
        }
        return RestResult.buildSuccessResponse();
    }

    public  RestResult<Empty> sendMarkdownIntoQueue(MarkDownRequestVo requestVo){
        try {
            amqpTemplate.convertAndSend(DingTalkDirectConfig.DING_TALK_EXCHANGE, DingTalkDirectConfig.DING_TALK_MARKDOWN, requestVo);
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
}
