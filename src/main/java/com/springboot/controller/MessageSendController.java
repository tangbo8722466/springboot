package com.springboot.controller;

import com.springboot.Utils.RestResult;
import com.springboot.Vo.response.Empty;
import com.springboot.dingTalkSdk.service.DingTalkService;
import com.springboot.dingTalkSdk.vo.request.LinkRequestVo;
import com.springboot.dingTalkSdk.vo.request.MarkDownRequestVo;
import com.springboot.dingTalkSdk.vo.request.TextRequestVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by tangbo on 2018/1/28 0028.
 */
@RestController
@RequestMapping("/v1/")
@Api(value = "钉钉群消息推送")
public class MessageSendController {

    @Autowired
    DingTalkService dingTalkService;

    @ApiOperation(value = "发送Text钉钉消息", notes = "发送Text钉钉消息", response = Empty.class)
    @RequestMapping(value = "/dingTalk/sendText",  method = RequestMethod.POST)
    RestResult<Empty> sendDingTalkTextMessage(@Valid @RequestBody TextRequestVo textRequestVo) {
        return dingTalkService.sendTextIntoQueue(textRequestVo);
    }

    @ApiOperation(value = "发送Link钉钉消息", notes = "发送Link钉钉消息", response = Empty.class)
    @RequestMapping(value = "/dingTalk/sendLink",  method = RequestMethod.POST)
    RestResult<Empty> sendDingTalkLinkMessage(@Valid @RequestBody LinkRequestVo linkRequestVo) {
        return dingTalkService.sendLinkIntoQueue(linkRequestVo);
    }

    @ApiOperation(value = "发送MarkDown钉钉消息", notes = "发送MarkDown钉钉消息", response = Empty.class)
    @RequestMapping(value = "/dingTalk/sendMarkDown",  method = RequestMethod.POST)
    RestResult<Empty> sendDingTalkMarkDownMessage(@Valid @RequestBody MarkDownRequestVo markDownRequestVo) {
        return dingTalkService.sendMarkdownIntoQueue(markDownRequestVo);
    }
}
